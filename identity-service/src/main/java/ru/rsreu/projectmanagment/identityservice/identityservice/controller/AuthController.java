package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LoginReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LogoutReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RefreshReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RegisterReqest;
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
    public AuthResponse register(@RequestBody @Valid RegisterReqest registerReqest){
        return authService.register(registerReqest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginReqest loginReqest){
        return authService.login(loginReqest);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshReqest refreshReqest){
        return authService.refresh(refreshReqest);
    }

    public AuthResponse logout(@RequestBody LogoutReqest logoutReqest){
        return authService.logout(logoutReqest);
    }

}
