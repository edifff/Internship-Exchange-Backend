package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Resume;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.StudentProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;

import java.time.Year;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StudentProfileMapper {
    StudentProfileDTO toDTO(StudentProfile studentProfile, User user);

    @Mapping(target = "graduationYear", source = "studentProfile.graduationYear")
    default Integer map(Year year) {
        return year != null ? year.getValue() : 2000;
    }

    default Year map(Integer year) {
        return year != null ? Year.of(year) : Year.of(2000);
    }

    @Mapping(target = "resume", source = "studentProfile.resume")
    default UUID map(Resume resume) {
        return resume.getId();
    }
}
