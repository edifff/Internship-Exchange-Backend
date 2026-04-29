package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class EmployerProfileRequest {

    @NotBlank
    @Schema(example = "Дрочильня штурвал удовольствия")
    private String companyName;

    @NotBlank
    @Schema(example = "Наши ручки готовы заменить ваши уставшие")
    private String description;

    @NotBlank
    @Schema(example = "https://github.com/edifff/Internship-Exchange-Backend")
    private String websiteLink;

    private UUID logo;

}
