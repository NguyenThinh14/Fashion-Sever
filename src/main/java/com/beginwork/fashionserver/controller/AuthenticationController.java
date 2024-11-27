package com.beginwork.fashionserver.controller;

import com.beginwork.fashionserver.dto.request.AuthenticationRequest;
import com.beginwork.fashionserver.dto.response.ApiResponse;
import com.beginwork.fashionserver.dto.response.AuthenticationResponse;
import com.beginwork.fashionserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){

        AuthenticationResponse authenticate = authenticationService.login(request.getEmail(), request.getPassword());

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(authenticate)
                .message("Login successfully").build();


    }
}
