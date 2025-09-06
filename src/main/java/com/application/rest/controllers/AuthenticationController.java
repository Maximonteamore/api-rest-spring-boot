package com.application.rest.controllers;

import com.application.rest.controllers.dto.AuthCreateUserRequest;
import com.application.rest.controllers.dto.AuthLoginRequest;
import com.application.rest.controllers.dto.AuthResponse;
import com.application.rest.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Controller for Authentication")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser) throws IllegalAccessException {
        return new ResponseEntity<>(this.userServiceImpl.createUser(authCreateUser),HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    @Operation(
            summary = "Login User",
            description = "Authenticate a user and return the authentication token along with user details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( //body del parametro de la funcion login
                    description = "Authentication request with username and password",
                    required = true,//indica que el parametro es obligatorio
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = AuthLoginRequest.class)//hace referencia al objeto dto que se recibe en el parametro.
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content( //contenido de la respuesta de metodo.
                                    mediaType = "application/json",
                                    schema = @Schema( implementation = AuthResponse.class)//hace referencia al objeto dto que se recibe en el parametro.
                            )

                    )
            }
    )
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userServiceImpl.loginUser(userRequest), HttpStatus.OK);

    }
}
