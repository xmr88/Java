package com.example.chatappds.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
public class Friends {
    @EmbeddedId
    private FriendsId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    private LocalDateTime createdAt;

    // Getters and Setters
    public FriendsId getId() {
        return id;
    }

    public void setId(FriendsId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}