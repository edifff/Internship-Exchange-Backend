package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "startedAt", target = "startDate")
    @Mapping(source = "endedAt", target = "endDate")
    Vacancy toEntity(CreateVacancyRequest request);

    @Mapping(source = "startDate", target = "startedAt")
    @Mapping(source = "endDate", target = "endedAt")
    VacancyDTO toDTO(Vacancy vacancy);

    @Mapping(source = "startDate", target = "startedAt")
    @Mapping(source = "endDate", target = "endedAt")
    List<VacancyDTO> toDTO(List<Vacancy> vacancies);
}
