package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.SpecialtiesRepository;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "startedAt", target = "startDate")
    @Mapping(source = "endedAt", target = "endDate")
    @Mapping(source = "specialties", target = "specialties", qualifiedByName = "uuidListToSpecialtySet")
    Vacancy toEntity(CreateVacancyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "startedAt", target = "startDate")
    @Mapping(source = "endedAt", target = "endDate")
    @Mapping(source = "specialties", target = "specialties", qualifiedByName = "uuidListToSpecialtySet")
    void updateVacancy(UpdateVacancyRequest request, @MappingTarget Vacancy vacancy);

    @Mapping(source = "employer.userId", target = "employerId")
    @Mapping(source = "startDate", target = "startedAt")
    @Mapping(source = "endDate", target = "endedAt")
    @Mapping(source = "specialties", target = "specialties", qualifiedByName = "specialtySetToStringList")
    VacancyDTO toDTO(Vacancy vacancy);

    List<VacancyDTO> toDTO(List<Vacancy> vacancies);

    @Named("uuidListToSpecialtySet")
    default Set<Specialty> mapUuidListToSpecialtySet(List<UUID> ids) {
        if (ids == null) return Collections.emptySet();
        return ids.stream().map(id -> {
            Specialty s = new Specialty();
            s.setId(id);
            return s;
        }).collect(Collectors.toSet());
    }

    @Named("specialtySetToStringList")
    default List<String> mapSpecialtySetToStringList(Set<Specialty> specialties) {
        if (specialties == null) return Collections.emptyList();
        return specialties.stream()
                .map(Specialty::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
