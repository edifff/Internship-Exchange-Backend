package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.EmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateStudentProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;

@Service
public class ProfileService {
    public ProfileDTO getMyProfil(Authentication auth) {
        return new ProfileDTO();
    }

    public StudentProfileDTO updateStudentProfile(Authentication auth, UpdateStudentProfileRequest reqest) {
        return new StudentProfileDTO();
    }

    public EmployerProfileDTO updateEmployerProfile(Authentication auth, EmployerProfileRequest reqest) {
        return null;
    }
}
