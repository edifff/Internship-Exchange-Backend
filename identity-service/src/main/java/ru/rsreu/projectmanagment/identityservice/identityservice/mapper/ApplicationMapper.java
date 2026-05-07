package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ApplicationDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Application;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    List<ApplicationDTO> toDTO(List<Application> applications);
}
