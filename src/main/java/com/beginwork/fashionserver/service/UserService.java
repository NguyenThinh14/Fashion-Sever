package com.beginwork.fashionserver.service;

import com.beginwork.fashionserver.dto.mapper.UserMapper;
import com.beginwork.fashionserver.dto.request.UserRequest;
import com.beginwork.fashionserver.dto.response.UserResponse;
import com.beginwork.fashionserver.entity.User;
import com.beginwork.fashionserver.exception.ResourceConflictException;
import com.beginwork.fashionserver.exception.ResourceNotFoundException;
import com.beginwork.fashionserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponses(users);
    }

    public UserResponse save(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail()))
            throw new ResourceConflictException("Email address already in use");
        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse findById(String id) {

        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    public UserResponse findByEmail(String email) {

        return userMapper.toUserResponse(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email address not found")));
    }

    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        userRepository.delete(user);
    }

    public UserResponse updateById(UserRequest userRequest, String id) {
       User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

       userMapper.updateUser(user, userRequest);

        return userMapper.toUserResponse(userRepository.save(user));

    }
}
