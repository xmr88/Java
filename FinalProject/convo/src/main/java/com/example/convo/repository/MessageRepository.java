package com.example.convo.repository;

import com.example.convo.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByChannelId(Integer channelId);

    List<Message> findByRecipientId(Integer recipientId);

    @Query("SELECT m FROM Message m WHERE (m.authorId = :userId1 AND m.recipientId = :userId2) " +
            "OR (m.authorId = :userId2 AND m.recipientId = :userId1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findMessagesBetweenUsers(@Param("userId1") Integer userId1, @Param("userId2") Integer userId2);

    Page<Message> findByChannelId(Integer channelId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE (m.authorId = :userId1 AND m.recipientId = :userId2) " +
            "OR (m.authorId = :userId2 AND m.recipientId = :userId1)")
    Page<Message> findMessagesBetweenUsers(@Param("userId1") Integer userId1,
                                           @Param("userId2") Integer userId2,
                                           Pageable pageable);


}
