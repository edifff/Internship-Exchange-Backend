package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ аутентификации: пара JWT-токенов")
public class AuthResponse {
    @Schema(description = "Access-токен (JWT, срок жизни 5 мин)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh-токен (срок жизни 2 дня)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "Тип токена", example = "Bearer")
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
