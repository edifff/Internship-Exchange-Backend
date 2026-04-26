package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Регистрация, вход, управление сессиями")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public AuthResponse register(@RequestBody @Valid RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему",
            description = "Возвращает пару JWT-токенов при успешной аутентификации")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление токенов", description = "Получение новой пары токенов по действующему refresh-токену")
    public AuthResponse refresh(@RequestBody RefreshRequest refreshRequest){
        return authService.refresh(refreshRequest);
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из системы", description = "Отзывает refresh-токен, завершая сессию")
    public void logout(@RequestBody LogoutRequest logoutRequest){
        authService.logout(logoutRequest);
    }

}
