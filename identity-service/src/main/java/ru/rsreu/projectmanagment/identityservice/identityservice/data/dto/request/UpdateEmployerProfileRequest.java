package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEmployerProfileRequest {
    private String companyName;

    private String description;

    private String websiteLink;

    private UUID logo;
}
