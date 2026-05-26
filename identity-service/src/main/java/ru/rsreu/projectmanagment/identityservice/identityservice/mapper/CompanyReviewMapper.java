package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.CompanyReviewDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.CompanyReview;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyReviewMapper {
    @Mapping(source = "student.userId", target = "studentId")
    @Mapping(source = "employer.userId", target = "employerId")
    CompanyReviewDTO toDTO(CompanyReview review);

    List<CompanyReviewDTO> toDTOList(List<CompanyReview> reviews);
}