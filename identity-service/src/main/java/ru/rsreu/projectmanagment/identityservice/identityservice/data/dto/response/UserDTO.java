package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Публичные данные пользователя")
public class UserDTO {
    @Schema(description = "Уникальный идентификатор", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @Schema(description = "Активен ли аккаунт")
    private boolean isActive;

    @Schema(description = "Назначенные роли")
    private Set<Role> roles;
}