package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.EmployerVacancyService;

import java.util.UUID;

@RestController
@RequestMapping("/employer/vacancies")
@AllArgsConstructor
@PreAuthorize("hasRole('EMPLOYER')")
@Tag(name = "Vacancies Management EMP", description = "C U D операции вакансий (только Employer)")
public class EmployerVacancyController {

    private final EmployerVacancyService employerVacancyService;

    @PostMapping("/")
    public void create(@RequestBody CreateVacancyRequest createVacancyResponse){
        employerVacancyService.create(createVacancyResponse);
    }

    @PatchMapping("/{id}")
    public VacancyDTO update(@PathVariable("id") UUID id, @RequestBody UpdateVacancyRequest request){
        return employerVacancyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void getAll(@PathVariable("id") UUID id){
        employerVacancyService.archive(id);
    }
}
