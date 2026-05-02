package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSpecialtyRequest {
    @NotBlank
    @Schema(example = "09.03.03")
    private String code;

    @NotBlank
    @Schema(example = "Прикладная информатика")
    private String name;

}
