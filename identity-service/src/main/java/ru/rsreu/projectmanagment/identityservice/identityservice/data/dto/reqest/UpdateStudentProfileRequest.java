package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;


import java.time.Year;

public class UpdateStudentProfileRequest {
    @Pattern(regexp = "^[^0-9]*$")
    private String fullName;

    @Min(1950)
    private Year graduationYear;
}
