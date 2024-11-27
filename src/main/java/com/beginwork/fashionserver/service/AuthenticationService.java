package com.beginwork.fashionserver.service;

import com.beginwork.fashionserver.constant.JwtHelper;
import com.beginwork.fashionserver.dto.response.AuthenticationResponse;
import com.beginwork.fashionserver.entity.User;
import com.beginwork.fashionserver.exception.ResourceNotFoundException;
import com.beginwork.fashionserver.exception.UnauthorizedException;
import com.beginwork.fashionserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtHelper jwtHelper;

    public AuthenticationResponse login(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email " +email+" not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean verifyPassword= passwordEncoder.matches(password,user.getPassword());
        if(!verifyPassword)
            throw new UnauthorizedException("Invalid Password");
        String token =jwtHelper.generateToken(email);



        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(verifyPassword)
                .build();

    }
}
