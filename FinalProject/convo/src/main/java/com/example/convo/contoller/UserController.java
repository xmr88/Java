package com.example.convo.contoller;

import com.example.convo.model.Channel;
import com.example.convo.model.User;
import com.example.convo.service.UserService;
import com.example.convo.http.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Връщане на всички потребители
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return AppResponse.success()
                .withMessage("Fetched all users successfully")
                .withData(users)
                .build();
    }

    // Търсене по ID на потребител
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return AppResponse.success()
                    .withMessage("User found")
                    .withData(user.get())
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("User not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    // Търсене по потребителско име
    @GetMapping("/search")
    public ResponseEntity<Object> getUserByUsername(@RequestParam String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return AppResponse.success()
                    .withMessage("User found")
                    .withData(user.get())
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("User not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    // Създаване на потребител
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        User user = userService.createUser(username);
        return AppResponse.success()
                .withMessage("User created successfully")
                .withData(user)
                .build();
    }

    // Добавяне на приятел
    @PostMapping("/{id}/add-friend")
    public ResponseEntity<Object> addFriend(@PathVariable Integer id, @RequestParam Integer friendId) {
        userService.addFriend(id, friendId);
        return AppResponse.success()
                .withMessage("Friend added successfully")
                .build();
    }

    // Изтриване на потребител по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return AppResponse.success()
                    .withMessage("User deleted successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("User not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    // Връщане на всички канали, в които участва потребителят
    @GetMapping("/{id}/channels")
    public ResponseEntity<Object> getUserChannels(@PathVariable Integer id) {
        List<Channel> channels = userService.getUserChannels(id);
        return AppResponse.success()
                .withMessage("Fetched user channels successfully")
                .withData(channels)
                .build();
    }

    // Връщане на всички приятели на потребителя
    @GetMapping("/{id}/friends")
    public ResponseEntity<Object> getUserFriends(@PathVariable Integer id) {
        Set<User> friends = userService.getUserFriends(id);
        return AppResponse.success()
                .withMessage("Fetched user friends successfully")
                .withData(friends)
                .build();
    }

    @GetMapping("/{id}/friends/paged")
    public ResponseEntity<Object> getUserFriendsPaged(@PathVariable Integer id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<User> friends = userService.getUserFriendsPaged(id, page, size);
        return AppResponse.success()
                .withMessage("Fetched paged friends successfully")
                .withData(friends.getContent())
                .build();
    }


}
