package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.StudentProfile;

import java.util.UUID;

public interface ProfileStudentRepository extends JpaRepository<StudentProfile, UUID> {
    StudentProfile findByUserId(UUID userId);

}
