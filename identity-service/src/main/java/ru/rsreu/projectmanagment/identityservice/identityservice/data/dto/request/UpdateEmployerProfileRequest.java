package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEmployerProfileRequest {

    @Schema(example = "Дрочильня штурвал удовольствия")
    private String companyName;

    @Schema(example = "Наши ручки готовы заменить ваши уставшие")
    private String description;

    @Schema(example = "https://github.com/edifff/Internship-Exchange-Backend")
    private String websiteLink;

    private UUID logo;
}
