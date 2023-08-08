package com.e2eEChatApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Tasin Al Nahian Khan",
                        email = "tasin.nahian@gmail.com",
                        url = "https://tasinnahian.github.io"
                ),
                description = "End-to-End Encrypted Chat Application OpenAPI documentation",
                version = "1.0.0",
                title = "Bengal Chat"
        ),
        servers = {
                @Server(
                        description = "AWS EC2 instance",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Component
public class OpenAPIConfig {
}
