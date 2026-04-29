package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateVacancyRequest {

    private UUID employerUUID;
    private String title;
    private String description;
    private String city;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private List<String> specialtys;

}
