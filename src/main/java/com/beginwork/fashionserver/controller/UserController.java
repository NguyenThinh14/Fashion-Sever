package com.beginwork.fashionserver.controller;

import com.beginwork.fashionserver.dto.request.UserRequest;
import com.beginwork.fashionserver.dto.response.ApiResponse;
import com.beginwork.fashionserver.dto.response.UserResponse;
import com.beginwork.fashionserver.entity.User;
import com.beginwork.fashionserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request) {

        return ApiResponse.<UserResponse>builder()
                .code(201)
                .message("User Created")
                .result(userService.save(request))
                .build();
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return userService.findAll();
    }

    @GetMapping("/find")
    public ApiResponse<UserResponse> getUserById(@RequestParam("id") String id) {

        UserResponse userResponse = userService.findById(id);

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get user by id success")
                .result(userResponse)
                .build();
    }


    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable String id) {

        return userService.updateById(userRequest,id);
    }

    @DeleteMapping("/delete")
    public String deleteUseByEmail(@RequestParam("email") String email) {

        userService.deleteByEmail(email);

        return "User with email " + email + " was deleted";
    }
}
