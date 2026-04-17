package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogoutRequest {
    @NotBlank
    private String refreshToken;
}
