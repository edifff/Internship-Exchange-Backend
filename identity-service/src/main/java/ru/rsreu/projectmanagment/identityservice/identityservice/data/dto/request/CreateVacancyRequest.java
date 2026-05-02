package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateVacancyRequest {

    @NotBlank
    private UUID employerUUID;

    @Schema(example = "Царь во дворца")
    private String title;

    @NotBlank
    @Schema(example = "King of the ring")
    private String description;

    @NotBlank
    @Schema(example = "Mordor")
    private String city;

    @NotBlank
    @Schema(example = "2026-03-15")
    private LocalDate startedAt;

    @NotBlank
    @Schema(example = "2026-06-15")
    private LocalDate endedAt;

    private List<String> specialtys;

}
