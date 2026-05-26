package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateCompanyReviewRequest {
    @NotNull
    @Schema(description = "ID компании-работодателя")
    private UUID employerId;

    @NotNull
    @Min(1) @Max(5)
    @Schema(description = "Оценка от 1 до 5", example = "5")
    private Short rating;

    @Size(max = 255)
    @Schema(description = "Текст отзыва", example = "Отличное место для практики")
    private String comment;
}