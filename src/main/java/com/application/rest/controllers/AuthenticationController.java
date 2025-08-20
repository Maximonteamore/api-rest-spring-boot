package com.application.rest.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    public ResponseEntity<AuthReponse> login(AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.loginUser(userRequest), HttpStatus.ok);
    }


}
