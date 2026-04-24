package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LoginRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.LogoutRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RefreshRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.RegisterRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.AuthResponse;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.*;

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
    private final ProfileStudentRepository profileStudentRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final ResumeRepository resumeRepository;
    private final FileEntityRepository fileEntityRepository;

    @Value("${api.security.token.refresh-expiration}")
    private long refreshExprirationDays;

    private AuthResponse buildAuthResponse(User user){
        String accessToken = jwtService.generateAccessToken(user);
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

    @Transactional
    public AuthResponse register(@Valid RegisterRequest request) {

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role"));
        
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        createProfileForRole(user, role);

        return buildAuthResponse(user);
    }

    private Resume createResumeForRole(StudentProfile studentProfile) {

        Resume resume=Resume.builder()
                .student(studentProfile)
                .createdAt(LocalDate.now())
                .titel("Resume "+studentProfile.getFullName())
                .build();

        studentProfile.setResume(resume);

        resumeRepository.save(resume);

        return resume;
    }

    private void createProfileForRole(User user, Role role) {
        String roleName = role.getName();

        switch (roleName) {
            case "ROLE_STUDENT" -> {
                StudentProfile profile = new StudentProfile();
                profile.setUser(user);

                profile = profileStudentRepository.save(profile);

                Resume resume = createResumeForRole(profile);

                FileEntity avatar = createFileEntuty(resume, user);

                profile.setResume(resume);
                profile.setAvatar(avatar);

                profileStudentRepository.save(profile);
            }
            case "ROLE_EMPLOYER" -> {
                EmployerProfile profile = new EmployerProfile();
                profile.setUser(user);

                profile = employerProfileRepository.save(profile);

                FileEntity logo = createFileEntuty(user);
                profile.setLogo(logo);

                employerProfileRepository.save(profile);
            }
            default -> throw new IllegalStateException("Unsupported role: " + roleName);
        }
    }

    //Для создвния аватарки компании
    private FileEntity createFileEntuty(User user) {

        FileEntity fileEntity=FileEntity.builder().owner(user)
                .createdAt(LocalDate.now())
                .build();

        fileEntityRepository.save(fileEntity);

        return fileEntity;
    }

    //Для создвния резюме компании
    private FileEntity createFileEntuty(Resume resume, User user) {

        FileEntity fileEntity=FileEntity.builder()
                .resume(resume)
                .owner(user)
                .createdAt(LocalDate.now())
                .build();

        fileEntityRepository.save(fileEntity);

        return fileEntity;
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {
        User user=userRepository.findByEmailWithRoles(loginRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())){
            throw new RuntimeException("Invalid credentials");
        }
        user.setLastLoginAt(Instant.now());
        
        userRepository.save(user);
        
        return buildAuthResponse(user);
    }

    @Transactional
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

        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(LogoutRequest logoutRequest) {

        RefreshToken token = refreshTokenRepository.findByToken(logoutRequest.getRefreshToken())
                .orElseThrow(()->new RuntimeException("Token not found"));
        token.setRevoked(true);

        refreshTokenRepository.save(token);
    }
}
