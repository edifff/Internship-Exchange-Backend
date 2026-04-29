package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateVacancyRequest {

    @Schema(example = "1c developer")
    private String title;

    @Schema(example = "imposable man in the world")
    private String description;

    @Schema(example = "Detroit")
    private String city;

    @Schema(example = "2026-03-15")
    private LocalDate startedAt;

    @Schema(example = "2026-06-15")
    private LocalDate endedAt;

    private List<String> specialtys;
}
