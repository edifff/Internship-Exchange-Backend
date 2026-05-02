package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.SpecialtyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.SpecialtyMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    public void create(CreateSpecialtyRequest createSpecialtyRequest) {
        Specialty specialty = mapper.toEntity(createSpecialtyRequest);
        specialty.setActive(true);

        repository.save(specialty);
    }

    public SpecialtyDTO update(UUID id, UpdateSpecialtyRequest request) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spesialty not found"));

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

        return mapper.toDTO(specialty);
    }

    public SpecialtyDTO get(UUID id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spesialty not found"));

        return mapper.toDTO(specialty);
    }

    public List<SpecialtyDTO> getAll() {
        List<Specialty> specialties = repository.findAll();

        return mapper.toDTO(specialties);
    }

    public void delete(UUID id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spesialty not found"));
        repository.delete(specialty);
    }
}
