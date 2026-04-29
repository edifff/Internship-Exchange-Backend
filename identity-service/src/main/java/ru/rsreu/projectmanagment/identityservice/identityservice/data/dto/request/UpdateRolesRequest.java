package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;

@Getter
public class UpdateRolesRequest {

    @Schema(example = "STUDENT")
    private RoleNames role;
}
