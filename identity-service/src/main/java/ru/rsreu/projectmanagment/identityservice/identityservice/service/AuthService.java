package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;

@Service
public class AuthService {


    public AuthResponse register(@Valid RegisterRequest registerRequest) {
        return null;
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {
        return null;
    }

    public AuthResponse refresh(RefreshRequest refreshRequest) {
        return null;
    }

    public AuthResponse logout(LogoutRequest logoutRequest) {
        return null;
    }
}
