package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.SpecialtyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.SpecialtyMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    public void create(CreateSpecialtyRequest createSpecialtyRequest) {
        log.info("Create specialty | Code: {}, Name: {}", createSpecialtyRequest.getCode(), createSpecialtyRequest.getName());
        Specialty specialty = mapper.toEntity(createSpecialtyRequest);
        specialty.setActive(true);

        repository.save(specialty);
    }

    public SpecialtyDTO update(UUID id, UpdateSpecialtyRequest request) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Spesialty not found"));

        if( request.getCode() != null ){
            specialty.setCode(request.getCode());
        }

        if( request.getName() != null ){
            specialty.setName(request.getName());
        }

        if( request.getIsActive() != specialty.isActive() ){
            specialty.setActive(request.getIsActive());
        }

        repository.save(specialty);
        log.info("Update specialty | Id: {}", id);

        return mapper.toDTO(specialty);
    }

    public SpecialtyDTO get(UUID id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Spesialty not found"));

        log.debug("Get specialty | Id: {}", id);
        return mapper.toDTO(specialty);
    }

    public List<SpecialtyDTO> getAll() {
        List<Specialty> specialties = repository.findAll();

        return mapper.toDTO(specialties);
    }

    public void delete(UUID id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Spesialty not found"));
        repository.delete(specialty);
        log.info("Delete specialty | Id: {}", id);

    }
}
