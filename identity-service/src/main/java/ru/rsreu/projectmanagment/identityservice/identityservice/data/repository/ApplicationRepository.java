package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    boolean existsByStudentUserIdAndVacancyId(
            UUID studentId,
            UUID vacancyId
    );

    List<Application> findAllByStudentUserId(UUID studentId);

    List<Application> findAllByVacancyEmployerUserId(UUID employerId);
}
