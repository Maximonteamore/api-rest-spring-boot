package com.application.rest.controllers.dto;

import jakarta.validation.constraints.NotBlank;

//  NOTBLANK es para validar que no venga vacio.
public record AuthLoginRequest(@NotBlank String username , @NotBlank String password) {
}
