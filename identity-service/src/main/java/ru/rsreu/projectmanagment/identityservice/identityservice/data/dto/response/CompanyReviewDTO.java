package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CompanyReviewDTO {
    @Schema(description = "ID отзыва") private UUID id;
    @Schema(description = "ID студента-автора") private UUID studentId;
    @Schema(description = "ID компании") private UUID employerId;
    @Schema(description = "Оценка") private Short rating;
    @Schema(description = "Комментарий") private String comment;
    @Schema(description = "Дата создания") private LocalDate createdAt;
}