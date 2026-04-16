package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest refreshRequest){
        return authService.refresh(refreshRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest){
         authService.logout(logoutRequest);
    }

}
