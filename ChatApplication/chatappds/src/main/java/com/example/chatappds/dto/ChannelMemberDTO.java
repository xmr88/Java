package com.example.chatappds.dto;

public class ChannelMemberDTO {
    private Long channelId;
    private Long userId;
    private String role;

    public ChannelMemberDTO(Long channelId, Long userId, String role) {
        this.channelId = channelId;
        this.userId = userId;
        this.role = role;
    }

    public ChannelMemberDTO() {}

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
