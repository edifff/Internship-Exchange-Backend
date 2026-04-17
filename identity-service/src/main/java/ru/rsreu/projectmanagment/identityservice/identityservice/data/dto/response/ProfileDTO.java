package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;


import lombok.Data;

@Data
public class ProfileDTO {
    private String email;
    private String role;

    private StudentProfileDTO studentProfile;
    private EmployerProfileDTO employerProfile;
}
