package com.example.chatappds.service;

import com.example.chatappds.dto.ChannelDTO;
import com.example.chatappds.model.Channel;
import com.example.chatappds.model.User;
import com.example.chatappds.model.ChannelMember;
import com.example.chatappds.repository.ChannelRepository;
import com.example.chatappds.repository.UserRepository;
import com.example.chatappds.repository.ChannelMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelMemberRepository channelMemberRepository;

    @Override
    public List<ChannelDTO> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(this::mapToChannelDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChannel(Long id) {
        channelRepository.deleteById(id);
    }

    @Override
    public ChannelDTO getChannelById(Long id) {
        Optional<Channel> channel = channelRepository.findById(id);
        if (channel.isPresent()) {
            return mapToChannelDTO(channel.get());
        } else {
            throw new RuntimeException("Channel not found");
        }
    }

    @Override
    public void renameChannel(Long channelId, String newName) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        if (newName == null || newName.trim().isEmpty()) {
            throw new RuntimeException("Channel name cannot be empty");
        }

        channel.setName(newName);
        channelRepository.save(channel);
    }


    @Override
    public ChannelDTO createChannel(ChannelDTO channelDTO) {
        Channel channel = new Channel();
        channel.setName(channelDTO.getName());

        // Fetch the owner (User) from the repository
        User owner = userRepository.findById(channelDTO.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        channel.setOwner(owner);

        channel.setCreatedAt(LocalDateTime.now());
        channel = channelRepository.save(channel);
        return mapToChannelDTO(channel);
    }

    public void addUserToChannel(Long channelId, Long userId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isAlreadyMember = channelMemberRepository.existsByChannelAndUser(channel, user);
        if (isAlreadyMember) {
            throw new RuntimeException("User is already a member of the channel");
        }

        ChannelMember newMember = new ChannelMember();
        newMember.setChannel(channel);
        newMember.setUser(user);
        newMember.setRole("MEMBER"); // Default role

        channelMemberRepository.save(newMember);
    }

    private ChannelDTO mapToChannelDTO(Channel channel) {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(channel.getId());
        channelDTO.setName(channel.getName());
        channelDTO.setOwnerId(channel.getOwner().getId());
        channelDTO.setOwnerName(channel.getOwner().getUsername());
        channelDTO.setCreatedAt(channel.getCreatedAt());
        return channelDTO;
    }
}
