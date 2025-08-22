package com.application.rest.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username,
                             @NotBlank String password,
                             @NotBlank String role) {
}
