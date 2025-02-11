package com.example.chatappds.controller;

import com.example.chatappds.dto.MessageDTO;
import com.example.chatappds.dto.SendMessageDTO;
import com.example.chatappds.service.MessageService;
import com.example.chatappds.http.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Object> sendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        try {
            MessageDTO messageDTO = messageService.sendMessage(sendMessageDTO);
            return AppResponse.success()
                    .withCode(HttpStatus.CREATED)
                    .withMessage("Message sent successfully")
                    .withData(messageDTO)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to send message: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/between")
    public ResponseEntity<Object> getMessagesBetweenUsers(
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        try {
            List<MessageDTO> messages = messageService.getMessagesBetweenUsers(senderId, recipientId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Messages retrieved successfully")
                    .withData(messages)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to retrieve messages: " + e.getMessage())
                    .build();
        }
    }


    @GetMapping
    public ResponseEntity<Object> getMessages(@RequestParam Long channelId) {
        try {
            List<MessageDTO> messages = messageService.getMessagesByChannelId(channelId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Messages retrieved successfully")
                    .withData(messages)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to retrieve messages: " + e.getMessage())
                    .build();
        }
    }
}