package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.RefreshToken;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RefreshTokenRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RoleRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.UserRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.ConflictException;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${api.security.token.refresh-expiration}")
    private long refreshExprirationDays;

    private AuthResponse buildAuthResponse(User user) {

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenRepository.save(RefreshToken.builder()
                .token(refreshToken)
                .email(user.getEmail())
                .expiresAt(LocalDate.now().plus(refreshExprirationDays, ChronoUnit.DAYS))
                .revoked(false)
                .build());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse register(@Valid RegisterRequest request) {
        log.info("Registration attempt for email: {}, role: {}", request.getEmail(), request.getRole());

        Role role = roleRepository.findByName(request.getRole()).orElseThrow(
                () -> new ConflictException("User already exists"));
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .createdAt(Instant.now())
                .build();

        user.addRole(role);

        try {

            user = userRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            throw new ConflictException("User already exists");

        }

        log.info("User registered successfully: {}", user.getEmail());
        return buildAuthResponse(user);
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {

        User user = userRepository.findByEmailWithRoles(loginRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            log.warn("Failed login attempt for email: {}", loginRequest.getEmail());
            throw new BadCredentialsException("Invalid username or password");
            }
            user.setLastLoginAt(Instant.now());

            userRepository.save(user);

        log.info("User logged in: {}", user.getEmail());
        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest refreshRequest) {

        RefreshToken stored = refreshTokenRepository.findByToken(refreshRequest.getRefreshToken())
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (stored.isRevoked()) {
            throw new BadCredentialsException("Refresh token has been revoked");
        }

        if (stored.getExpiresAt().isBefore(LocalDate.now())) {
            throw new BadCredentialsException("Refresh token has expired");
        }

        DecodedJWT jwt = jwtService.verifyToken(refreshRequest.getRefreshToken());

        String email = jwtService.extractUserEmail(jwt);

        User user = userRepository.findByEmailWithRoles(email).orElseThrow(
                () -> new NotFoundException("User not found"));

        log.debug("Token refreshed for user: {}", email);
        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(LogoutRequest logoutRequest) {
        RefreshToken token = refreshTokenRepository.findByToken(logoutRequest.getRefreshToken()).orElseThrow(
                () -> new BadCredentialsException("Token not found"));
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}