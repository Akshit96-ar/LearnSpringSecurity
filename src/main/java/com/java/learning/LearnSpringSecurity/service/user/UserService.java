package com.java.learning.LearnSpringSecurity.service.user;

import com.java.learning.LearnSpringSecurity.model.Users;
import com.java.learning.LearnSpringSecurity.repository.user.UserRepository;
import com.java.learning.LearnSpringSecurity.service.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        // encrypt the password before saving
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(Users user) {
        Authentication authn =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authn.isAuthenticated()) {
            // generate a token instead of returning login success
            //return "Login Success!!! " + user.getUsername();  // basic dao auth flow

            return jwtService.generateToken(user.getUsername());
        }
        return "Login Failed!!!";
    }
}
