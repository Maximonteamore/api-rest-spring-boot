package com.application.rest.controllers;

import com.application.rest.controllers.dto.AuthCreateRoleRequest;
import com.application.rest.controllers.dto.AuthCreateUserRequest;
import com.application.rest.controllers.dto.AuthLoginRequest;
import com.application.rest.controllers.dto.AuthResponse;
import com.application.rest.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserServiceImpl userServiceImpl;

    // =========================
    // REGISTER OK
    // =========================
    @Test
    void register_OK() throws Exception {

        AuthCreateUserRequest request = new AuthCreateUserRequest(
                "maxim",
                "123456",
                new AuthCreateRoleRequest(List.of("USER"))
        );

        AuthResponse response = new AuthResponse(
                "maxim",
                "User created successfully",
                "jwt-token-123",
                true
        );

        when(userServiceImpl.createUser(any(AuthCreateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("maxim"))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.jwt").value("jwt-token-123"))
                .andExpect(jsonPath("$.status").value(true));
    }

    // =========================
    // REGISTER BAD REQUEST (VALIDATION)
    // =========================
    @Test
    void register_BadRequest() throws Exception {

        String json = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // LOGIN OK
    // =========================
    @Test
    void login_OK() throws Exception {

        AuthLoginRequest request = new AuthLoginRequest(
                "maxim",
                "123456"
        );

        AuthResponse response = new AuthResponse(
                "maxim",
                "Login successful",
                "jwt-login-456",
                true
        );

        when(userServiceImpl.loginUser(any(AuthLoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("maxim"))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.jwt").value("jwt-login-456"))
                .andExpect(jsonPath("$.status").value(true));
    }

    // =========================
    // LOGIN BAD REQUEST
    // =========================
    @Test
    void login_BadRequest() throws Exception {

        String json = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}

