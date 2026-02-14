package com.application.rest.controllers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

//@Valid
@Validated
public record AuthCreateRoleRequest(@Size(max = 2, message = "No puede tener mas de 2 roles") List<String> roleListName) {
}
