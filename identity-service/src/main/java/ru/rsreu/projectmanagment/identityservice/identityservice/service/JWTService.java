package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.access-expiration}")
    private long accessExpirationMinutes;

    @Value("${api.security.token.refresh-expiration}")
    private long refreshExprirationDays;

    private Algorithm getAlgoritm(){
        return Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(User user){
        try{
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(accessExpirationMinutes, ChronoUnit.MINUTES))
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("roles", user.getRoles()
                            .stream()
                            .map(Role::getName)
                            .toList())
                    .sign(getAlgoritm());
        }catch (JWTCreationException exception){
            throw new RuntimeException("Ошибка при генерации JWT токена", exception);
        }
    }

    public String generateRefreshToken(User user){
        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(refreshExprirationDays, ChronoUnit.DAYS))
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(getAlgoritm());
        } catch (JWTCreationException exception){
            throw new RuntimeException("Ошибка при генерации JWT токена", exception);
        }

    }

    public DecodedJWT verifyToken(String token){
        try{
            return JWT.require(getAlgoritm())
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Недействительный JWT токен", exception);
        }
    }

    public String extractUserEmail(DecodedJWT jwt){
        return jwt.getSubject();
    }

    public List<Role> extractRoles( DecodedJWT decodedJWT){
        return decodedJWT.getClaim("roles").asList(Role.class);
    }
}
