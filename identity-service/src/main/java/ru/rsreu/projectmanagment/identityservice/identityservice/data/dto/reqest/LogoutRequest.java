package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest;

import jakarta.validation.constraints.NotBlank;

public class LogoutRequest {
    @NotBlank
    private String refreshToken;
}
