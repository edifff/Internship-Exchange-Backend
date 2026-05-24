package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.SpecialtiesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specialty")
@AllArgsConstructor
@Tag(name = "Specialty Managment", description = "CRUD специальностей")
public class SpecialtiesController {

    private final SpecialtiesService service;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Только для админа")
    @ResponseStatus(HttpStatus.CREATED)
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
    public SpecialtyDTO get(@PathVariable("id") UUID id){
        return service.getDTO(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Только для админа")
    public void delete(@PathVariable("id") UUID id){
        service.delete(id);
    }

    @GetMapping("")
    @Operation(summary = "Получить все")
    public List<SpecialtyDTO> getAll(){
        return service.getAll();
    }

}
