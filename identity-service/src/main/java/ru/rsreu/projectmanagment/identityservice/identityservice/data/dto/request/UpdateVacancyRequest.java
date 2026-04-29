package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateVacancyRequest {
    private String title;
    private String description;
    private String city;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private List<String> specialtys;
}
