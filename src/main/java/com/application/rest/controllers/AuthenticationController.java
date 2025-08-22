package com.application.rest.controllers;




import com.application.rest.controllers.dto.AuthCreateUser;
import com.application.rest.controllers.dto.AuthLoginRequest;
import com.application.rest.controllers.dto.AuthResponse;
import com.application.rest.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUser authCreateUser){
        return new ResponseEntity<>(this.userServiceImpl.createUser(authCreateUser),HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userServiceImpl.loginUser(userRequest), HttpStatus.OK);

    }
}
