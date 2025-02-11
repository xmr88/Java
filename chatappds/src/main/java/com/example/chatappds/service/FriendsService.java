package com.example.chatappds.service;

import com.example.chatappds.dto.FriendsDTO;
import com.example.chatappds.dto.AddFriendDTO;

import java.util.List;

public interface FriendsService {
    FriendsDTO addFriend(AddFriendDTO addFriendDTO);
    List<FriendsDTO> getFriendsByUserId(Long userId);
    void removeFriend(Long userId, Long friendId);
    String getFriendshipStatus(Long userId, Long friendId);
}
