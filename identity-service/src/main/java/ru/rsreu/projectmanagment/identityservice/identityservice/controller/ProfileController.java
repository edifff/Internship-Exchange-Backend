package ru.rsreu.projectmanagment.identityservice.identityservice.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.EmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateStudentProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/me")
    public ProfileDTO getMyProfile(Authentication auth){
        return profileService.getNyProfile(auth);
    }

    @PutMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public StudentProfileDTO updateStudent(Authentication auth, @RequestBody UpdateStudentProfileRequest reqest){
        return profileService.updateStudentProfile(auth, reqest);
    }

    @PutMapping("/employer")
    @PreAuthorize("hasRole('EMPLOYER')")
    public EmployerProfileDTO updateEmployer(Authentication auth, @RequestBody EmployerProfileRequest reqest){
        return profileService.updateEmployerProfile(auth, reqest);
    }
}