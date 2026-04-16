package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.RefreshToken;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RefreshTokenRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RoleRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("{api.security.token.refresh-expiration}")
    private long refreshExprirationDays;

    private AuthResponse buildAuthResponse(User user){
        String accessToken = jwtService.gemerateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .email(user.getEmail())
                        .expiresAt(LocalDate.now().plus(refreshExprirationDays, ChronoUnit.DAYS))
                        .revoked(false)
                        .build()
        );
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse register(@Valid RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        Role role = roleRepository.findByName(RoleNames.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        User user=User.builder()
                .email(registerRequest.getEmail())
                .passwordHash(registerRequest.getPassword())
                .build();
        userRepository.save(user);
        return buildAuthResponse(user);
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())){
            throw new RuntimeException("Invalid credentials");
        }
        if(!user.isEnabled()){
            throw new RuntimeException("User is disabled");
        }
        return buildAuthResponse(user);
    }

    public AuthResponse refresh(RefreshRequest refreshRequest) {
        RefreshToken stored=refreshTokenRepository.findByToken(refreshRequest.getRefreshToken())
                .orElseThrow(()->new RuntimeException("Invalid refresh token"));
        if(stored.isRevoked()){
            throw new RuntimeException("Refresh token revoked");
        }
        if(stored.getExpiresAt().isBefore(LocalDate.now())){
            throw new RuntimeException("Refresh token revoked");
        }
        DecodedJWT jwt=jwtService.verifyToken(refreshRequest.getRefreshToken());
        String email= jwtService.extractUserEmail(jwt);

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    public void logout(LogoutRequest logoutRequest) {
        RefreshToken token = refreshTokenRepository.findByToken(logoutRequest.getRefreshToken())
                .orElseThrow(()->new RuntimeException("Token not found"));
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
