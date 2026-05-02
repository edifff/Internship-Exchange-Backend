package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;

import java.util.List;

@Mapper
public interface SpecialtyMapper {

    Specialty toEntity(CreateSpecialtyRequest createSpecialtyRequest);

    SpecialtyDTO toDTO(Specialty specialty);

    List<SpecialtyDTO> toDTO(List<Specialty> specialties);
}
