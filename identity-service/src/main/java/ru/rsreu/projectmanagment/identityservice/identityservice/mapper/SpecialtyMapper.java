package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateSpecialtyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.SpecialtyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;

import java.util.List;

@Mapper
public interface SpecialtyMapper {
    @Mapping(target = "active", source = "active") // Lombok @Builder может конфликтовать, явно указываем
    Specialty toEntity(CreateSpecialtyRequest createSpecialtyRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "isActive", source = "active")
    SpecialtyDTO toDTO(Specialty specialty);

    List<SpecialtyDTO> toDTO(List<Specialty> specialties);
}
