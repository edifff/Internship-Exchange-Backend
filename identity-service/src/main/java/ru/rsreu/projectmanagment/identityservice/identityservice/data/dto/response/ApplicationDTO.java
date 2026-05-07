package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ApplicationDTO {
    private UUID id;

    private UUID vacancyId;

    private String vacancyTitle;

    private UUID studentId;

    private String studentName;

    private UUID resumeId;

    private String coverLetter;

    private ApplicationStatus status;

    private LocalDate createdAt;
}
