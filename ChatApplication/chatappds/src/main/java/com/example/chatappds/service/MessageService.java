package com.example.chatappds.service;

import com.example.chatappds.dto.MessageDTO;
import com.example.chatappds.dto.SendMessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO sendMessage(SendMessageDTO sendMessageDTO);
    List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long recipientId); // NEW METHOD
    List<MessageDTO> getMessagesByChannelId(Long channelId); // Add this method

}