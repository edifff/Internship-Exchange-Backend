package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.EmployerProfileReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateStudentProfileReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;

@Service
public class ProfileService {
    public ProfileDTO getNyProfile(Authentication auth) {
        return new ProfileDTO();
    }

    public StudentProfileDTO updateStudentProfile(Authentication auth, UpdateStudentProfileReqest reqest) {
        return new StudentProfileDTO();
    }

    public EmployerProfileDTO updateEmployerProfile(Authentication auth, EmployerProfileReqest reqest) {
        return null;
    }
}
