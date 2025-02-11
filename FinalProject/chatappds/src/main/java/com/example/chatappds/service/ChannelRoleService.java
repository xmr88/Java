package com.example.chatappds.service;

import com.example.chatappds.dto.ChannelMemberDTO;
import com.example.chatappds.dto.AssignRoleDTO;

import java.util.List;

public interface ChannelRoleService {
    ChannelMemberDTO assignRole(AssignRoleDTO assignRoleDTO);
    List<ChannelMemberDTO> getChannelMembers(Long channelId);
    ChannelMemberDTO updateMemberRole(Long channelId, Long userId, String newRole);
    void removeMember(Long channelId, Long userId);

}