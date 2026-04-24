package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;

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
