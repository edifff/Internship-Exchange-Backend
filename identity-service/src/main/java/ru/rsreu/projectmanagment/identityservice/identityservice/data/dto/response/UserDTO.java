package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import java.util.Set;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String email;
    private boolean isActive;
    private Set<String> roles;
}
