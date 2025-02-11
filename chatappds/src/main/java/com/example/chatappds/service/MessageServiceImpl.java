package com.example.chatappds.service;

import com.example.chatappds.dto.MessageDTO;
import com.example.chatappds.dto.SendMessageDTO;
import com.example.chatappds.model.Message;
import com.example.chatappds.model.User;
import com.example.chatappds.model.Channel;
import com.example.chatappds.repository.MessageRepository;
import com.example.chatappds.repository.UserRepository;
import com.example.chatappds.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public MessageDTO sendMessage(SendMessageDTO sendMessageDTO) {
        if (sendMessageDTO.getContent() == null || sendMessageDTO.getContent().trim().isEmpty()) {
            throw new RuntimeException("Message content cannot be empty");
        }
        if (sendMessageDTO.getSenderId() == null) {
            throw new RuntimeException("Sender ID cannot be null");
        }
        if (sendMessageDTO.getChannelId() == null && sendMessageDTO.getRecipientId() == null) {
            throw new RuntimeException("Either channelId or recipientId must be provided");
        }

        User sender = userRepository.findById(sendMessageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setContent(sendMessageDTO.getContent());

        if (sendMessageDTO.getChannelId() != null) {
            Channel channel = channelRepository.findById(sendMessageDTO.getChannelId())
                    .orElseThrow(() -> new RuntimeException("Channel not found"));
            message.setChannel(channel);
        } else if (sendMessageDTO.getRecipientId() != null) {
            User recipient = userRepository.findById(sendMessageDTO.getRecipientId())
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));
            message.setRecipient(recipient);
        }

        message = messageRepository.save(message);
        return mapToMessageDTO(message);
    }

    public List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long recipientId) {
        List<Message> messages = messageRepository.findMessagesBetweenUsers(senderId, recipientId);
        return messages.stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getMessagesByChannelId(Long channelId) {
        List<Message> messages = messageRepository.findByChannelId(channelId);
        return messages.stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO mapToMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setChannelId(message.getChannel() != null ? message.getChannel().getId() : null);
        messageDTO.setRecipientId(message.getRecipient() != null ? message.getRecipient().getId() : null);
        messageDTO.setContent(message.getContent());
        messageDTO.setSentAt(message.getSentAt());
        return messageDTO;
    }
}