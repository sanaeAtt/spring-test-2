package com.example.taskmanager.service;

import java.util.Optional;
import com.example.taskmanager.model.Users;

public interface UserService {
    public Users registerUser(Users user) ;
    public Optional<Users> findByUsername(String username) ;
    public Optional<Users> findByEmail(String email) ;
}
