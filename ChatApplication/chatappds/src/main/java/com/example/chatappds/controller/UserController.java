package com.example.chatappds.controller;

import com.example.chatappds.dto.CreateUserDTO;
import com.example.chatappds.dto.LoginRequest;
import com.example.chatappds.dto.UserDTO;
import com.example.chatappds.http.AppResponse;
import com.example.chatappds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            UserDTO userDTO = userService.createUser(createUserDTO);
            return AppResponse.success()
                    .withCode(HttpStatus.CREATED)
                    .withMessage("User created successfully")
                    .withData(userDTO)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to create user: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDTO userDTO = userService.login(loginRequest);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Login successful")
                    .withData(userDTO)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.UNAUTHORIZED)
                    .withMessage("Login failed: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("User retrieved successfully")
                    .withData(userDTO)
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("User not found: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        try {
            UserDTO userDTO = userService.getUserByUsername(username);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("User retrieved successfully")
                    .withData(userDTO)
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("User not found: " + e.getMessage())
                    .build();
        }
    }

}