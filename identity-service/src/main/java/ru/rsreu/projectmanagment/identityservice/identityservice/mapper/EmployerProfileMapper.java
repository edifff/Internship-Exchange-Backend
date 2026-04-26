package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;

@Mapper(componentModel = "spring")
public interface EmployerProfileMapper {
    EmployerProfileDTO toDTO(EmployerProfile employerProfile);
}
