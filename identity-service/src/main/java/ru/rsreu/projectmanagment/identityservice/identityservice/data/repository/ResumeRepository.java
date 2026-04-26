package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Resume;

import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {
}

