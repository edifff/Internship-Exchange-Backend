package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;

import java.util.List;

@Mapper
public interface SpecialtiesMapper {
    List<Specialty> toEntity(List<String> specialtyStrings);

    default Specialty map(String value) {
        if (value == null) {
            return null;
        }

        return Specialty.builder()
                .name(value)
                .build();
    }
}
