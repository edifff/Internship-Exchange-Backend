package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;

import java.util.List;
import java.util.UUID;

public interface VacancyRepository extends JpaRepository<Vacancy, UUID>, JpaSpecificationExecutor<Vacancy> {

    List<Vacancy> findAllByEmployer(EmployerProfile employerProfile);

    List<Vacancy> findAllByEmployerAndDeletedAtIsNull(EmployerProfile employer);

    List<Vacancy> findAllByEmployerAndDeletedAtIsNotNull(EmployerProfile employer);

    List<Vacancy> findAllByStatusAndDeletedAtIsNull(Status status);
}
