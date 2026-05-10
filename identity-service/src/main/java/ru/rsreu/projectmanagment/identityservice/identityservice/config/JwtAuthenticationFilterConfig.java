package ru.rsreu.projectmanagment.identityservice.identityservice.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.CustomUserDetailService;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.JWTService;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilterConfig extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.debug("JWT Filter triggered. Header: {}", authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                DecodedJWT jwt = jwtService.verifyToken(token);

                String email = jwtService.extractUserEmail(jwt);

                UserDetails userDetails = userDetailService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authentication successful for user: {}. Authorities: {}", email, userDetails.getAuthorities());


            } catch (Exception e) {
                log.warn("JWT Auth failed | URI: {}, Error: {}", request.getRequestURI(), e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}