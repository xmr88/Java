package com.example.convo.repository;

import com.example.convo.model.Channel;
import com.example.convo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    Optional<Channel> findByName(String name);
    List<Channel> findByMembersContaining(User user);
    Page<Channel> findAll(Pageable pageable);

}

