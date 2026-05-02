package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateSpecialtyRequest {
    @Schema(example = "09.03.03")
    private String code;

    @Schema(example = "Перекладная информатика")
    private String name;

    @Schema(example = "false")
    private boolean isActive;

    public boolean getIsActive(){
        return this.isActive;
    }
}
