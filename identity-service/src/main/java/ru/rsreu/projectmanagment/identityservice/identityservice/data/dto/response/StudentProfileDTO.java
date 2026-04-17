package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Data;

import java.time.Year;

@Data
public class StudentProfileDTO {
    private String fullName;
    private Year graduationYear;
    private String avatarUrl;
}
