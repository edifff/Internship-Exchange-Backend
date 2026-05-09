package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateApplicationRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ApplicationDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
@Tag(name = "Application Managment", description = "Операции с откликами вакансий")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "Отклик на вакансию")
    public void apply(@RequestBody @Valid CreateApplicationRequest request, Authentication auth) {
        applicationService.apply(request, auth);
    }

    @GetMapping("/my")
    @Operation(summary = "Мои отклики")
    public List<ApplicationDTO> my(Authentication auth) {
        return applicationService.getMyApplications(auth);
    }

    @GetMapping("/employer")
    @Operation(summary = "Отклик на вакансию")
    public List<ApplicationDTO> employer(Authentication auth) {
        return applicationService.getEmployerApplications(auth);
    }
}
