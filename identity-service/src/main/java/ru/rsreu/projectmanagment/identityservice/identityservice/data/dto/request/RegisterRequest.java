package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;

@Getter
@Schema(description = "Запрос на регистрацию")
public class RegisterRequest {
    @Email
    @NotBlank
    @Schema(description = "Email пользователя", example = "student@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Пароль (6–100 символов)", example = "SecurePass123", minLength = 6, maxLength = 100)
    private String password;

    @NotNull
    @Schema(description = "Роль пользователя в системе", example = "ROLE_STUDENT")
    private RoleNames role;
}
