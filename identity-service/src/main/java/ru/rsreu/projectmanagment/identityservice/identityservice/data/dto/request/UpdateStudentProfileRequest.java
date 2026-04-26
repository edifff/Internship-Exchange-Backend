package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


import java.time.Year;
import java.util.UUID;

@Getter
public class UpdateStudentProfileRequest {
    @Pattern(regexp = "^[^0-9]*$")
    private String fullName;

    @Min(value = 1959, message = "graduation Year cant be less than 1950")
    private Integer graduationYear;

    private UUID resume;

    private UUID avatar;

}