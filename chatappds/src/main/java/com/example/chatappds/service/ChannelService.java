package com.example.chatappds.service;

import com.example.chatappds.dto.ChannelDTO;

import java.util.List;

public interface ChannelService {

    List<ChannelDTO> getAllChannels();
    void renameChannel(Long channelId, String newName);
    void deleteChannel(Long id);
    ChannelDTO createChannel(ChannelDTO channelDTO); // Add this method
    ChannelDTO getChannelById(Long id); // Add this method
    void addUserToChannel(Long channelId, Long userId);
}