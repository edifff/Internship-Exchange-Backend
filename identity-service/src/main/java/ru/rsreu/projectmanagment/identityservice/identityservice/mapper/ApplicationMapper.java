package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ApplicationDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Application;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    List<ApplicationDTO> toDTO(List<Application> applications);

    @Mapping(source = "vacancy.id", target = "vacancyId")
    @Mapping(source = "vacancy.title", target = "vacancyTitle")
    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "resume.id", target = "resumeId")
    ApplicationDTO toDTO(Application application);
}
