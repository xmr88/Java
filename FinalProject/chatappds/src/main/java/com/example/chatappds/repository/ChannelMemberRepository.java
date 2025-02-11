package com.example.chatappds.repository;

import com.example.chatappds.model.Channel;
import com.example.chatappds.model.ChannelMember;
import com.example.chatappds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {
    Optional<ChannelMember> findByChannelIdAndUserId(Long channelId, Long userId);
    List<ChannelMember> findByChannel(Channel channel);
    boolean existsByChannelAndUser(Channel channel, User user);

}