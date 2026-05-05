package ru.rsreu.projectmanagment.identityservice.identityservice.data.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class VacancyFilter {
    @Schema(example = "Ведьмак")
    private String title;

    @Schema(example = "Новиград")
    private String city;

    @Schema(example = "09.03.2026")
    private LocalDate startedAt;

    @Schema(example = "09.04.2026")
    private LocalDate endedAt;

    private UUID specialtyId;
}
