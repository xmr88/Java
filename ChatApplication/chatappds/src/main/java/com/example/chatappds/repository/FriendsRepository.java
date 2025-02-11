package com.example.chatappds.repository;

import com.example.chatappds.model.Friends;
import com.example.chatappds.model.FriendsId;
import com.example.chatappds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, FriendsId> {
    List<Friends> findByUser(User user);
    List<Friends> findByUserId(Long userId);
    Optional<Friends> findByUserIdAndFriendId(Long userId, Long friendId);
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}