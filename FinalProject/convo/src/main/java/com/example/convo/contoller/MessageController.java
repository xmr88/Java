package com.example.convo.contoller;

import com.example.convo.model.Message;
import com.example.convo.service.MessageService;
import com.example.convo.http.AppResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Изпращане на съобщение до канал или потребител по ID
    @PostMapping
    public ResponseEntity<Object> sendMessage(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        Integer authorId = Integer.parseInt(payload.get("authorId"));
        Integer channelId = payload.get("channelId") != null ? Integer.parseInt(payload.get("channelId")) : null;
        Integer recipientId = payload.get("recipientId") != null ? Integer.parseInt(payload.get("recipientId")) : null;

        Message message = messageService.sendMessage(content, authorId, channelId, recipientId);
        return AppResponse.success()
                .withMessage("Message sent successfully")
                .withData(message)
                .build();
    }

    // Връщане на съобщения от канал по ID
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<Object> getMessagesByChannelId(@PathVariable Integer channelId) {
        List<Message> messages = messageService.getMessagesByChannelId(channelId);
        if (messages.isEmpty()) {
            return AppResponse.error()
                    .withMessage("No messages found in the channel")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Fetched messages from channel successfully")
                .withData(messages)
                .build();
    }

    // Връщане на съобщения от потребител по ID
    @GetMapping("/user/{recipientId}")
    public ResponseEntity<Object> getMessagesByRecipientId(@PathVariable Integer recipientId) {
        List<Message> messages = messageService.getMessagesByRecipientId(recipientId);
        if (messages.isEmpty()) {
            return AppResponse.error()
                    .withMessage("No messages found for the user")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Fetched messages for the user successfully")
                .withData(messages)
                .build();
    }

    @PostMapping("/friend")
    public ResponseEntity<Object> sendMessageToFriend(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        Integer authorId = Integer.parseInt(payload.get("authorId"));
        Integer recipientId = Integer.parseInt(payload.get("recipientId"));

        Message message = messageService.sendMessageToFriend(content, authorId, recipientId);
        return AppResponse.success()
                .withMessage("Message sent successfully")
                .withData(message)
                .build();
    }

    @GetMapping("/conversation")
    public ResponseEntity<Object> getMessagesBetweenUsers(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        List<Message> messages = messageService.getMessagesBetweenUsers(userId1, userId2);
        if (messages.isEmpty()) {
            return AppResponse.error()
                    .withMessage("No messages found between users")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Fetched messages successfully")
                .withData(messages)
                .build();
    }

    @GetMapping("/channel/{channelId}/paged")
    public ResponseEntity<Object> getMessagesByChannelPaged(@PathVariable Integer channelId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<Message> messages = messageService.getMessagesByChannelPaged(channelId, page, size);
        return AppResponse.success()
                .withMessage("Fetched paged messages from channel successfully")
                .withData(messages.getContent())
                .build();
    }


    @GetMapping("/conversation/paged")
    public ResponseEntity<Object> getMessagesBetweenUsersPaged(@RequestParam Integer userId1,
                                                               @RequestParam Integer userId2,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Page<Message> messages = messageService.getMessagesBetweenUsersPaged(userId1, userId2, page, size);
        return AppResponse.success()
                .withMessage("Fetched paged messages successfully")
                .withData(messages.getContent())
                .build();
    }
    // WebSocket метод за изпращане на съобщения
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message broadcastMessage(Message message) {
        return message;
    }
}
