package com.example.chatappds.controller;

import com.example.chatappds.dto.FriendsDTO;
import com.example.chatappds.dto.AddFriendDTO;
import com.example.chatappds.http.AppResponse;
import com.example.chatappds.service.FriendsService;
import com.example.chatappds.service.FriendsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendService;

    @PostMapping
    public ResponseEntity<Object> addFriend(@RequestBody AddFriendDTO addFriendDTO) {
        try {
            FriendsDTO newFriendship = friendService.addFriend(addFriendDTO);
            return AppResponse.success()
                    .withCode(HttpStatus.CREATED)
                    .withMessage("Friend added successfully")
                    .withData(newFriendship)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Error adding friend: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getFriends(@PathVariable Long userId) {
        try {
            List<FriendsDTO> friends = friendService.getFriendsByUserId(userId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Friends retrieved successfully")
                    .withData(friends)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Error retrieving friends: " + e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<Object> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            friendService.removeFriend(userId, friendId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Friend removed successfully")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Error removing friend: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/status/{userId}/{friendId}")
    public ResponseEntity<Object> getFriendshipStatus(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            String status = friendService.getFriendshipStatus(userId, friendId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Friendship status retrieved successfully")
                    .withData(status)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Error retrieving friendship status: " + e.getMessage())
                    .build();
        }
    }
}
