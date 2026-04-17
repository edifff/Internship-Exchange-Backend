package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Data;

@Data
public class EmployerProfileDTO {
    private String companyName;
    private String description;
    private String websiteLink;
    private String logoUrl;
}
