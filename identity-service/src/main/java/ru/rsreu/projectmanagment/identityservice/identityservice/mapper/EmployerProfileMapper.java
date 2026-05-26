package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;

@Mapper(componentModel = "spring")
public interface EmployerProfileMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "logo.id", target = "logoUrl")
    EmployerProfileDTO toDTO(EmployerProfile employerProfile);
}
