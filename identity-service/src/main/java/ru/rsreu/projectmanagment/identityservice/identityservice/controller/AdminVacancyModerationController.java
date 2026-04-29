package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.AdminVacancyModerationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/vacancies/pending")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminVacancyModerationController {

    private final AdminVacancyModerationService adminVacancyModerationService;

    @GetMapping("/getAll")
    public List<VacancyDTO> getPendingVacancy(){
        return adminVacancyModerationService.getPendingVacancy();
    }

    @PatchMapping("{id}")
    public void setStatus(@PathVariable UUID id, String status){
        adminVacancyModerationService.setStatus(id, status);
    }
}
