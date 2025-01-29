package com.example.convo.service;

import com.example.convo.model.Channel;
import com.example.convo.model.User;
import com.example.convo.repository.ChannelRepository;
import com.example.convo.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Optional<Channel> getChannelById(Integer id) {
        return channelRepository.findById(id);
    }

    public Optional<Channel> getChannelByName(String name) {
        return channelRepository.findByName(name);
    }

    public Channel createChannel(String name, Integer ownerId) {
        Channel channel = new Channel(name, ownerId);
        return channelRepository.save(channel);
    }

    public void addMember(Channel channel, User user) {
        channel.getMembers().add(user);
        channelRepository.save(channel);
    }

    public void deleteChannel(Integer id) {
        channelRepository.deleteById(id);
    }

    public void deleteChannelByOwner(Integer channelId, Integer ownerId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        // Проверка дали потребителят е собственик
        if (!channel.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("User is not the owner of the channel");
        }

        // Изтриване на канала
        channelRepository.delete(channel);
    }


    public void removeMemberByOwner(Integer channelId, Integer ownerId, Integer memberId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        // Проверка дали потребителят е собственик
        if (!channel.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("User is not the owner of the channel");
        }

        // Проверка дали членът съществува в канала
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!channel.getMembers().contains(member)) {
            throw new RuntimeException("Member is not part of the channel");
        }

        // Премахване на члена
        channel.getMembers().remove(member);
        channelRepository.save(channel);
    }

    public void assignAdminRole(Integer channelId, Integer ownerId, Integer userId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        // Проверка дали потребителят е собственик
        if (!channel.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Only the owner can assign roles");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Задаване на роля "Администратор"
        channel.getRoles().put(user, "ADMIN");
        channelRepository.save(channel);
    }

    public boolean isAdmin(Integer channelId, Integer userId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return "ADMIN".equals(channel.getRoles().get(user));
    }

    public void addMemberByAdmin(Integer channelId, Integer adminId, Integer memberId) {
        if (!isAdmin(channelId, adminId)) {
            throw new RuntimeException("Only admins can add members");
        }

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        channel.getMembers().add(member);
        channelRepository.save(channel);
    }

    public Page<Channel> getAllChannelsPaged(int page, int size) {
        return channelRepository.findAll(PageRequest.of(page, size));
    }

}

