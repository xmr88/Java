package com.example.chatappds.service;

import com.example.chatappds.dto.CreateUserDTO;
import com.example.chatappds.dto.LoginRequest;
import com.example.chatappds.dto.UserDTO;

public interface UserService {
    UserDTO createUser(CreateUserDTO createUserDTO);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO login(LoginRequest loginRequest);

}