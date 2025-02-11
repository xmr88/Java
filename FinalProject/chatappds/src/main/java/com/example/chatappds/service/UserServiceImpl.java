package com.example.chatappds.service;

import com.example.chatappds.dto.CreateUserDTO;
import com.example.chatappds.dto.LoginRequest;
import com.example.chatappds.dto.UserDTO;
import com.example.chatappds.model.User;
import com.example.chatappds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return mapToUserDTO(user);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user = userRepository.save(user);
        return mapToUserDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return mapToUserDTO(user);
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }
}