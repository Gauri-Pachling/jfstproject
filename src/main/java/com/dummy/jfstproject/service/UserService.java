package com.dummy.jfstproject.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.dummy.jfstproject.model.User;
import com.dummy.jfstproject.repository.UserRepo;


@Service
public class UserService {

    private final UserRepo userRepo;


    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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

}