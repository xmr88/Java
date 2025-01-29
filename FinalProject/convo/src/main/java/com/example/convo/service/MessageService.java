package com.example.convo.service;

import com.example.convo.model.Message;
import com.example.convo.model.User;
import com.example.convo.repository.MessageRepository;
import com.example.convo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(String content, Integer authorId, Integer channelId, Integer recipientId) {
        Message message = new Message(content, authorId, channelId, recipientId);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Integer channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    public List<Message> getMessagesByRecipientId(Integer recipientId) {
        return messageRepository.findByRecipientId(recipientId);
    }

    public Message sendMessageToFriend(String content, Integer authorId, Integer recipientId) {
        User sender = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        // Проверка дали са приятели
        if (!sender.getFriends().contains(recipient)) {
            throw new RuntimeException("Users are not friends");
        }

        // Създаване на съобщение
        Message message = new Message(content, authorId, null, recipientId);
        return messageRepository.save(message);
    }
    public List<Message> getMessagesBetweenUsers(Integer userId1, Integer userId2) {
        return messageRepository.findMessagesBetweenUsers(userId1, userId2);
    }

    public Page<Message> getMessagesByChannelPaged(Integer channelId, int page, int size) {
        return messageRepository.findByChannelId(channelId, PageRequest.of(page, size));
    }

    public Page<Message> getMessagesBetweenUsersPaged(Integer userId1, Integer userId2, int page, int size) {
        return messageRepository.findMessagesBetweenUsers(userId1, userId2, PageRequest.of(page, size));
    }


}
