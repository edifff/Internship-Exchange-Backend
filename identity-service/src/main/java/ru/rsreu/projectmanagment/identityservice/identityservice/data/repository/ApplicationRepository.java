package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    boolean existsByStudentUserIdAndVacancyId(UUID studentId, UUID vacancyId);
    List<Application> findAllByStudentUserId(UUID studentId);
    List<Application> findAllByVacancyEmployerUserId(UUID employerId);

    // Новый метод: отклики работодателя за период
    @Query("SELECT a FROM Application a " +
            "JOIN a.vacancy v " +
            "JOIN v.employer e " +
            "WHERE e.userId = :employerId AND a.createdAt >= :since")
    List<Application> findRecentByEmployerId(
            @Param("employerId") UUID employerId,
            @Param("since") LocalDate since
    );
}
