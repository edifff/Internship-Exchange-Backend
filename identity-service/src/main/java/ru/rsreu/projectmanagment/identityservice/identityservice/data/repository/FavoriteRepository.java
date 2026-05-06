package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Favorite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<Favorite> findByStudentUserIdAndVacancyId(UUID studentId, UUID vacancyId);

    List<Favorite> findAllByStudentUserId(UUID studentId);

    void deleteByStudentUserIdAndVacancyId(UUID studentId, UUID vacancyId);

    boolean existsByStudentUserIdAndVacancyId(UUID studentId, UUID vacancyId);
}
