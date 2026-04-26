package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email
    @NotBlank
    @Schema(example = "admin@gmail.com")
    private String email;

    @NotBlank
    @Schema(example = "SecurePass123")
    private String password;
}
