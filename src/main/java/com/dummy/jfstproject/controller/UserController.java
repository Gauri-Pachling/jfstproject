package com.dummy.jfstproject.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.jfstproject.Authentication.JwtResponse;
import com.dummy.jfstproject.Authentication.JwtUtil;
import com.dummy.jfstproject.model.User;
import com.dummy.jfstproject.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        System.out.println("Received username: " + loginUser.getUsername());
        Optional<User> userOptional = userService.getUserByUsername(loginUser.getUsername());
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Database username: " + user.getUsername());
            System.out.println("Database password: " + user.getPassword());
            System.out.println("Received password: " + loginUser.getPassword());
    
            if (user.getPassword().equals(loginUser.getPassword())) {
                // Generate token
                String token = jwtUtil.generateToken(user.getUsername());
                
                // Return the token in the response
                return ResponseEntity.ok(new JwtResponse(token, user));  // JwtResponse contains the token
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } else {
            return ResponseEntity.status(401).body("User not found");
        }
    }
    
}