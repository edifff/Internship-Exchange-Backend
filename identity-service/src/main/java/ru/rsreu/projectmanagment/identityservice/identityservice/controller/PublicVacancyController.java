package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

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
public class PublicVacancyController {
    private final PublicVacancyService publicVacancyService;

    @GetMapping("/{id}")
    public VacancyDTO get(@PathVariable UUID vacancy){
       return publicVacancyService.get(vacancy);
    }

    @GetMapping("/all")
    public List<VacancyDTO> getAll(){
        return publicVacancyService.getAll();
    }

    @GetMapping("/all/{id}")
    public List<VacancyDTO> getAllCompanyVacancy(@PathVariable UUID id){
        return publicVacancyService.getAllCompanyVacancy(id);
    }

    @GetMapping("/allActivity/{id}")
    public List<VacancyDTO> getAllActivityCompanyVacancy(@PathVariable UUID id){
        return publicVacancyService.getAllActivityCompanyVacancy(id);
    }

    @GetMapping("/allDeleted/{id}")
    public List<VacancyDTO> getAllDeletedCompanyVacancy(@PathVariable UUID id){
        return  publicVacancyService.getAllDeletedCompanyVacancy(id);
    }

}
