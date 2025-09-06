package com.application.rest.controllers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

//dto para crear un usuario.
public record AuthCreateUserRequest(@NotBlank String username,
                             @NotBlank String password,
                             @Valid AuthCreateRoleRequest roleRequest) {
}
