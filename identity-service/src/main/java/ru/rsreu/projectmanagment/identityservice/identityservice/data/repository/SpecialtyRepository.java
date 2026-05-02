package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;

import java.util.List;
import java.util.UUID;

public interface SpecialtyRepository extends JpaRepository<Specialty, UUID> {

    List<Specialty> id(UUID id);
}
