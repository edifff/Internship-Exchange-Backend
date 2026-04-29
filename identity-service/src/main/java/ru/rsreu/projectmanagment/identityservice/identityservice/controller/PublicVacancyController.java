package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.PublicVacancyService;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vacancies")
@AllArgsConstructor
@Tag(name = "Getting Vacancies", description = "различное получение вакансий (доступно всем)")
public class PublicVacancyController {
    private final PublicVacancyService publicVacancyService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение конкретной вакансии")
    public VacancyDTO get(@PathVariable("id") UUID vacancy){
       return publicVacancyService.get(vacancy);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех одобренных неархивированных вакансий")
    public List<VacancyDTO> getAll(){
        return publicVacancyService.getAll();
    }

    @GetMapping("/all/{id}")
    @Operation(summary = "Получение всех компании")
    public List<VacancyDTO> getAllCompanyVacancy(@PathVariable("id") UUID id){
        return publicVacancyService.getAllCompanyVacancy(id);
    }

    @GetMapping("/allActivity/{id}")
    @Operation(summary = "Получение всех одобренных неархивированных вакансий")
    public List<VacancyDTO> getAllActivityCompanyVacancy(@PathVariable("id") UUID id){
        return publicVacancyService.getAllActivityCompanyVacancy(id);
    }

    @GetMapping("/allDeleted/{id}")
    @Operation(summary = "Получение всех одобренных архивированных вакансий")
    public List<VacancyDTO> getAllDeletedCompanyVacancy(@PathVariable("id") UUID id){
        return  publicVacancyService.getAllDeletedCompanyVacancy(id);
    }

}
