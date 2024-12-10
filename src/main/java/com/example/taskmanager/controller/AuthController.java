package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.request.UserLoginRequestDto;
import com.example.taskmanager.dto.request.UserRegisterRequestDto;
import com.example.taskmanager.dto.response.LoginResponseDto;
import com.example.taskmanager.model.Users;
import com.example.taskmanager.security.JwtUtils;
import com.example.taskmanager.service.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
// @AllArgsConstructor
// @NoArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private UserServiceImpl userServiceImpl;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    public AuthController(UserServiceImpl userServiceImpl, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/test")
public ResponseEntity<String> test() {
    return ResponseEntity.ok("Test endpoint works!");
}
    // @PostMapping("/register")
    // public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {

    //     System.out.println("Trying to register user: " + userRegisterRequestDto.getUsername());

    //     // * if already exist :) */

    //     if (userServiceImpl.findByUsername(userRegisterRequestDto.getUsername()).isPresent()) {
    //         return ResponseEntity.status(HttpStatus.CONFLICT).body("User Alreadyy Exist");
        
    //     }
    //     // if (userServiceImpl.findByEmail(userRegisterRequestDto.getEmail()).isPresent()) {
    //     //     return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
    //     // }
    //     Users user = new Users();
    //     user.setUserName(userRegisterRequestDto.getUsername());
    //     user.setEmail(userRegisterRequestDto.getEmail());
    //     user.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));

    //     userServiceImpl.registerUser(user);
    //     System.out.println("User created successfully: " + user.getUserName());

    //     return ResponseEntity.ok("User Created Succefly :)");
    // }

    @PostMapping("/register")
    public String register() {
        System.out.println("Register method called");
        return "Method called";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
    
        Optional<Users> optional = userServiceImpl.findByUsername(userLoginRequestDto.getUsername());
        if (optional.isPresent()) {
            Users user= optional.get();
            //*password verification */

            if (passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
                String token = jwtUtils.generateJwtToken(user.getUserName());
                            LoginResponseDto response = new LoginResponseDto(token);

                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials :/");
    }
    

}
