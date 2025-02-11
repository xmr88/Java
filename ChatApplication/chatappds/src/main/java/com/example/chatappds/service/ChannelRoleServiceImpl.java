package com.example.chatappds.service;

import com.example.chatappds.dto.ChannelMemberDTO;
import com.example.chatappds.dto.AssignRoleDTO;
import com.example.chatappds.model.Channel;
import com.example.chatappds.model.ChannelMember;
import com.example.chatappds.model.User;
import com.example.chatappds.repository.ChannelMemberRepository;
import com.example.chatappds.repository.ChannelRepository;
import com.example.chatappds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelRoleServiceImpl implements ChannelRoleService {

    @Autowired
    private ChannelMemberRepository channelMemberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChannelMemberDTO assignRole(AssignRoleDTO assignRoleDTO) {
        Channel channel = channelRepository.findById(assignRoleDTO.getChannelId())
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        User user = userRepository.findById(assignRoleDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ChannelMember> existingMember = channelMemberRepository.findByChannelIdAndUserId(channel.getId(), user.getId());
        ChannelMember channelMember;

        if (existingMember.isPresent()) {
            channelMember = existingMember.get();
            channelMember.setRole(assignRoleDTO.getRole());
        } else {
            channelMember = new ChannelMember();
            channelMember.setChannel(channel);
            channelMember.setUser(user);
            channelMember.setRole(assignRoleDTO.getRole());
        }

        channelMember = channelMemberRepository.save(channelMember);
        return mapToChannelMemberDTO(channelMember);
    }

    private ChannelMemberDTO mapToChannelMemberDTO(ChannelMember channelMember) {
        ChannelMemberDTO channelMemberDTO = new ChannelMemberDTO();
        channelMemberDTO.setChannelId(channelMember.getChannel().getId());
        channelMemberDTO.setUserId(channelMember.getUser().getId());
        channelMemberDTO.setRole(channelMember.getRole());
        return channelMemberDTO;
    }

    @Override
    public List<ChannelMemberDTO> getChannelMembers(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        List<ChannelMember> channelMembers = channelMemberRepository.findByChannel(channel);
        return channelMembers.stream()
                .map(this::mapToChannelMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChannelMemberDTO updateMemberRole(Long channelId, Long userId, String newRole) {
        ChannelMember member = channelMemberRepository.findByChannelIdAndUserId(channelId, userId)
                .orElseThrow(() -> new RuntimeException("Member not found in this channel"));

        member.setRole(newRole);
        channelMemberRepository.save(member);

        return new ChannelMemberDTO(member.getChannel().getId(), member.getUser().getId(), member.getRole());
    }

    @Override
    public void removeMember(Long channelId, Long userId) {
        ChannelMember channelMember = channelMemberRepository.findByChannelIdAndUserId(channelId, userId)
                .orElseThrow(() -> new RuntimeException("Channel member not found"));
        channelMemberRepository.delete(channelMember);
    }
}