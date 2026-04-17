package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import lombok.Getter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;

@Getter
public class UpdateRolesRequest {
    private RoleNames role;
}
