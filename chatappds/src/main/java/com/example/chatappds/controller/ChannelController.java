package com.example.chatappds.controller;

import com.example.chatappds.dto.ChannelDTO;
import com.example.chatappds.http.AppResponse;
import com.example.chatappds.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping
    public ResponseEntity<Object> getAllChannels() {
        try {
            List<ChannelDTO> channels = channelService.getAllChannels();
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Channels retrieved successfully")
                    .withData(channels)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to retrieve channels: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createChannel(@RequestBody ChannelDTO channelDTO) {
        try {
            ChannelDTO createdChannel = channelService.createChannel(channelDTO);
            return AppResponse.success()
                    .withCode(HttpStatus.CREATED)
                    .withMessage("Channel created successfully")
                    .withData(createdChannel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to create channel: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<Object> getChannelById(@PathVariable Long channelId) {
        try {
            ChannelDTO channel = channelService.getChannelById(channelId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Channel retrieved successfully")
                    .withData(channel)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Channel not found: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{channelId}/addUser")
    public ResponseEntity<Object> addUserToChannel(@PathVariable Long channelId, @RequestParam Long userId) {
        try {
            channelService.addUserToChannel(channelId, userId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("User added to channel successfully")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to add user to channel: " + e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{channelId}/rename")
    public ResponseEntity<Object> renameChannel(@PathVariable Long channelId, @RequestParam String newName) {
        try {
            channelService.renameChannel(channelId, newName);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Channel renamed successfully")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to rename channel: " + e.getMessage())
                    .build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteChannel(@PathVariable Long id) {
        try {
            channelService.deleteChannel(id);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Channel deleted successfully")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to delete channel: " + e.getMessage())
                    .build();
        }
    }
}
