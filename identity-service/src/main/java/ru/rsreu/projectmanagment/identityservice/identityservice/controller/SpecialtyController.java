package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.SpecialtyService;

import java.util.UUID;

@RestController
@RequestMapping("/specialty")
@AllArgsConstructor
@Tag(name = "Specialty Managment", description = "CRUD специальностей")
public class SpecialtyController {

    private final SpecialtyService service;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Только для админа")
    public void create(@RequestBody CreateSpecialtyRequest createSpecialtyRequest){
        service.create(createSpecialtyRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Только для админа")
    public SpecialtyDTO update(@PathVariable("id") UUID id, @RequestBody UpdateSpecialtyRequest request){
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить конкретную")
    public void get(@PathVariable("id") UUID id){
        service.get(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Только для админа")
    public void delete(@PathVariable("id") UUID id){
        service.delete(id);
    }

    @GetMapping("")
    @Operation(summary = "Получить все")
    public void getAll(){
        service.getAll();
    }

}
