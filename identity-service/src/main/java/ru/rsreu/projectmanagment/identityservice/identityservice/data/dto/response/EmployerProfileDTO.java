package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerProfileDTO {
    private String companyName;
    private String description;
    private String websiteLink;
    private UUID logoUrl;
}
