package com.example.taskmanager.service.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.taskmanager.model.Users;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.UserService;

import lombok.AllArgsConstructor;

@Service
// @AllArgsConstructor
public class UserServiceImpl implements UserService{

private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
}

    @Override
    public Users registerUser(Users user) {
       return userRepository.save(user);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
       return userRepository.findByUserName(username);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
