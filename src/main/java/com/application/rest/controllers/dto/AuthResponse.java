package com.application.rest.controllers.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

@JsonPropertyOrder({"username", "message","jwt"}) //con esto indico el orden que quiero que jackson serialice la respuesta.
public record AuthResponse(String username, String message, String jwt, boolean status) {
}
