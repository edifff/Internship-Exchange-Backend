package ru.rsreu.projectmanagment.identityservice.identityservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.EmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateEmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateStudentProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.ProfileService;

@RestController
@AllArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/me")
    public ProfileDTO getMyProfile(Authentication auth){
        System.out.println("AUTH IN CONTROLLER: " + auth);
        return profileService.getMyProfil(auth);
    }

    @PatchMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Обновление профиля",
            description = "Доступен только с ролью студента")
    public StudentProfileDTO updateStudent(Authentication auth, @RequestBody UpdateStudentProfileRequest reqest){
        return profileService.updateStudentProfile(auth, reqest);
    }

    @PatchMapping("/employer")
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Обновление профиля",
            description = "Доступен только с ролью компании")
    public EmployerProfileDTO updateEmployer(Authentication auth, @RequestBody UpdateEmployerProfileRequest reqest){
        return profileService.updateEmployerProfile(auth, reqest);
    }
}