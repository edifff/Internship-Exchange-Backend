package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class VacancyDTO {
    private UUID employerUUID;
    private String title;
    private String description;
    private String city;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private List<String> specialtys;
}
