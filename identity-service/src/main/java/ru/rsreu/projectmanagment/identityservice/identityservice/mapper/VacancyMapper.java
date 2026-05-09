package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.handler.SpecialtyHandler;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = SpecialtyHandler.class
)
public interface VacancyMapper {

    @Mapping(source = "startedAt", target = "startDate")
    @Mapping(source = "endedAt", target = "endDate")
    @Mapping(
            target = "specialties",
            source = "specialtys",
            qualifiedByName = "mapSpecialties"
    )
    Vacancy toEntity(CreateVacancyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(source = "startedAt", target = "startDate")
    @Mapping(source = "endedAt", target = "endDate")
    @Mapping(
            target = "specialties",
            source = "specialtys",
            qualifiedByName = "mapSpecialties"
    )
    void updateVacancy(UpdateVacancyRequest request,
            @MappingTarget Vacancy vacancy
    );

    @Mapping(source = "startDate", target = "startedAt")
    @Mapping(source = "endDate", target = "endedAt")
    VacancyDTO toDTO(Vacancy vacancy);

    @Mapping(source = "startDate", target = "startedAt")
    @Mapping(source = "endDate", target = "endedAt")
    List<VacancyDTO> toDTO(List<Vacancy> vacancies);
}
