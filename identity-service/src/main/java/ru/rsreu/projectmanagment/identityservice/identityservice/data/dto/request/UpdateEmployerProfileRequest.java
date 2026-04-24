package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;

import java.util.UUID;

@Getter
public class UpdateEmployerProfileRequest {
    @NotBlank
    private String companyName;

    @NotBlank
    private String description;

    private String websiteLink;

    private UUID logo;
}
