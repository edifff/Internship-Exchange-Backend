package ru.rsreu.projectmanagment.identityservice.identityservice.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.EmployerProfileReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateStudentProfileReqest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;

    @PostMapping("/me")
    public ProfileDTO getMyProfile(Authentication auth){
        return profileService.getNyProfile(auth);
    }

    @PutMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public StudentProfileDTO updateStudent(Authentication auth, @RequestBody UpdateStudentProfileReqest reqest){
        return profileService.updateStudentProfile(auth, reqest);
    }

    @PutMapping("/employer")
    @PreAuthorize("hasRole('EMPLOYER')")
    public EmployerProfileDTO updateEmployer(Authentication auth, @RequestBody EmployerProfileReqest reqest){
        return profileService.updateEmployerProfile(auth, reqest);
    }




}
