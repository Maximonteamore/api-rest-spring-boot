package com.application.rest.configuration;

//Configuracion de swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;

@OpenAPIDefinition(
        info = @Info(
                title = "API PRODUCTOS Y FABRICANTES",
                description = "La app permite hacer crud sobre productos y fabricantes",
                termsOfService = "www.miapp.com/terminos_y_servicios",
                version = "1.0.0",
                contact = @Contact(
                        name = "maxi",
                        url = "www.miapp.com/contact",
                        email = "maxi@gamil.com"
                ),
                license = @License(
                        name = "licencia de uso estandar para sofware de miapp",
                        url = "www.miapp.com/licence",
                        identifier = "a ca se pone la licencia si tendria 12323"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://dominiodeproducicon:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)

@SecurityScheme( //habilito el token para usar en swagger.
        name = "Security Token",
        description = "Access token for my API",
        type = SecuritySchemeType.HTTP, //para el token.
        paramName = HttpHeaders.AUTHORIZATION,//CONFIGURACION DE LA AUTORIZACION EN EL HEADER
        in = SecuritySchemeIn.HEADER,//donde envio el token en el header
        scheme = "bearer",
        bearerFormat = "JWT"//TIPO DE FORMATO JWT
)
public class SwaggerConfig {
}
