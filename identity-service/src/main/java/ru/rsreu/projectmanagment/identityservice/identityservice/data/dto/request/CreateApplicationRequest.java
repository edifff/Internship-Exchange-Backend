package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateApplicationRequest {

    @NotNull
    private UUID vacancyId;

    @NotNull
    private UUID resumeId;

    @Size(max = 255)
    private String coverLetter;
}
