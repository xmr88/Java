package com.example.chatappds.service;

import com.example.chatappds.dto.FriendsDTO;
import com.example.chatappds.dto.AddFriendDTO;
import com.example.chatappds.model.Friends;
import com.example.chatappds.model.FriendsId;
import com.example.chatappds.model.User;
import com.example.chatappds.repository.FriendsRepository;
import com.example.chatappds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FriendsDTO addFriend(AddFriendDTO addFriendDTO) {
        User user = userRepository.findById(addFriendDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(addFriendDTO.getFriendId())
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        FriendsId friendsId = new FriendsId();
        friendsId.setUserId(user.getId());
        friendsId.setFriendId(friend.getId());

        Friends friends = new Friends();
        friends.setId(friendsId);
        friends.setUser(user);
        friends.setFriend(friend);
        friends = friendsRepository.save(friends);

        return mapToFriendsDTO(friends);
    }

    @Override
    public String getFriendshipStatus(Long userId, Long friendId) {
        boolean userAddedFriend = friendsRepository.existsByUserIdAndFriendId(userId, friendId);
        boolean friendAddedUser = friendsRepository.existsByUserIdAndFriendId(friendId, userId);

        if (userAddedFriend && friendAddedUser) {
            return "Friend";
        } else if (userAddedFriend) {
            return "Pending";
        } else {
            return "Not Friends";
        }
    }


    @Override
    public List<FriendsDTO> getFriendsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Friends> friendsList = friendsRepository.findByUser(user);
        return friendsList.stream()
                .map(this::mapToFriendsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        Friends friendship = friendsRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));
        friendsRepository.delete(friendship);
    }

    private FriendsDTO mapToFriendsDTO(Friends friends) {
        FriendsDTO friendsDTO = new FriendsDTO();
        friendsDTO.setUserId(friends.getUser().getId());
        friendsDTO.setFriendId(friends.getFriend().getId());
        friendsDTO.setCreatedAt(friends.getCreatedAt());
        return friendsDTO;
    }
}