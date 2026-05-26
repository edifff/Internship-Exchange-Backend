package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Data;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class VacancyDTO {
    private UUID id;
    private UUID employerId;
    private String title;
    private String description;
    private String city;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Status status;
    private List<String> specialties;
}
