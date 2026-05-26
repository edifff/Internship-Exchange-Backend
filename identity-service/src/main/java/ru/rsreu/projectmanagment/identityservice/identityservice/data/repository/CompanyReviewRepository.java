package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.CompanyReview;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, UUID> {
    List<CompanyReview> findByEmployerUserId(UUID employerId);
    boolean existsByStudentUserIdAndEmployerUserId(UUID studentId, UUID employerId);
    Optional<CompanyReview> findByIdAndStudentUserId(UUID id, UUID studentId);
    List<CompanyReview> findByStudentUserId(UUID studentId);
}
