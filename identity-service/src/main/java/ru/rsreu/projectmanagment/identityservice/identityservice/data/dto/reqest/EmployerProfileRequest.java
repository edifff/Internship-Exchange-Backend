package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmployerProfileRequest {
    @NotBlank
    private String companyName;

    @NotBlank
    private String description;

    private String websiteLink;

}
