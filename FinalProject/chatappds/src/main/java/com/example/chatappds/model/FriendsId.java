package com.example.chatappds.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendsId implements Serializable {
    private Long userId;
    private Long friendId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendsId friendsId = (FriendsId) o;
        return Objects.equals(userId, friendsId.userId) && Objects.equals(friendId, friendsId.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}