package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Vacancies Management ADM", description = "подтверждение и обновление статуса (только ADMIN)")
public class AdminVacancyModerationController {

    private final AdminVacancyModerationService adminVacancyModerationService;

    @GetMapping("/getAll")
    @Operation(summary = "Получение всех не одобренных вакансий")
    public List<VacancyDTO> getPendingVacancy(){
        return adminVacancyModerationService.getPendingVacancy();
    }

    @PatchMapping("{id}")
    @Operation(summary = "Изменение статуса вакансии после рассмотрения")
    public void setStatus(@PathVariable("id") UUID id,@RequestBody String status){
        adminVacancyModerationService.setStatus(id, status);
    }
}
