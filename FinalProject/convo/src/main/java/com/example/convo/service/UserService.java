package com.example.convo.service;

import com.example.convo.model.User;
import com.example.convo.model.Channel;
import com.example.convo.repository.UserRepository;
import com.example.convo.repository.ChannelRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ChannelRepository channelRepository;

    //Създаване на потребител
    public User createUser(String username) {
        User user = new User(username);
        return userRepository.save(user);
    }

    //Връщане на всички потребители
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Връщане на потребител по ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    //Търсене на потребител по потребителско име
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //Изтриване на потребител по ID
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Добавяне на приятел
    public void addFriend(Integer userId, Integer friendId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    //Извличане на приятели
    public Set<User> getUserFriends(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getFriends();
    }

    //Извличане на канали
    public List<Channel> getUserChannels(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ArrayList<>(user.getChannels());
    }

    public Page<User> getUserFriendsPaged(Integer userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> friends = new ArrayList<>(user.getFriends());
        int start = Math.min(page * size, friends.size());
        int end = Math.min((page + 1) * size, friends.size());

        return new PageImpl<>(friends.subList(start, end), PageRequest.of(page, size), friends.size());
    }

}
