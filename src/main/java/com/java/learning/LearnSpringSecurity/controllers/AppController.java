package com.java.learning.LearnSpringSecurity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        return "Welcome to Spring Security Demo : " + request.getSession().getId();
    }

    @PostMapping("/enter-name/{name}")
    public String printName(@PathVariable String name) {
        return "Hey!! "+ name + ". Great to see you learning new things.";
    }

    //get the csrf token ->part of basic authorization -> important for other request like post, put, update, patch, delete
    // need not to implement for when using bearer token approach
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

}

