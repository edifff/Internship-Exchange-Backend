package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;

import java.util.UUID;

public interface SpecialtiesRepository extends JpaRepository<Specialty, UUID> {

    Specialty findByName(String name);
}
