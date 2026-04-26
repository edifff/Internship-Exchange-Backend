package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;

import java.util.UUID;

public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, UUID> {
    EmployerProfile findByUserId(UUID userId);
}