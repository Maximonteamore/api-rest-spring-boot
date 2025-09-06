package com.application.rest.controllers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

@Valid
public record AuthCreateRoleRequest(@Size(max = 2, message = "No puede tener mas de 2 roles") List<String> roleListName) {
}
