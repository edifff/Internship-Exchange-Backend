package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LoginReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LogoutReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RefreshReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RegisterReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;

@Service
public class AuthService {


    public AuthResponse register(@Valid RegisterReqest registerReqest) {
        return null;
    }

    public AuthResponse login(@Valid LoginReqest loginReqest) {
        return null;
    }

    public AuthResponse refresh(RefreshReqest refreshReqest) {
        return null;
    }

    public AuthResponse logout(LogoutReqest logoutReqest) {
        return null;
    }
}
