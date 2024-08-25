package com.java.learning.LearnSpringSecurity.controllers;

import com.java.learning.LearnSpringSecurity.model.Users;
import com.java.learning.LearnSpringSecurity.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private UserService userService;

    /**
     * Method to register user and password
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }
}
