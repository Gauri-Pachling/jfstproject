package com.dummy.jfstproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dummy.jfstproject.model.User;
import com.dummy.jfstproject.repository.UserRepo;
import com.dummy.jfstproject.Authentication.*;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;

    }

     @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;


    
    // Method to authenticate the user
    public User authenticate(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByUsername(username));
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
    


    public Optional<User> getUserByUsername(String username) {
        System.out.println("Querying for username: " + username); // Debugging log
        Optional<User> user = userRepo.findByUsernameCustom(username);

        if (user.isPresent()) {
            System.out.println("User found: " + user.get().getUsername());
        } else {
            System.out.println("User not found");
        }
        return user;
    }

    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepo.findAllById(userIds).stream().collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // Fetch the user by their username (you can also use other identifiers)
            return userRepo.findByUsername(username);  // Assuming you have a method to fetch a user by username
        }
        return null;
    }
}