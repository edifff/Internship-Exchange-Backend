package ru.rsreu.projectmanagment.identityservice.identityservice.entity;

import jakarta.persistence.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.entity.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "employer_id")
    private EmployerProfile employerProfiles;

    @Column(name = "title")
    private String title;

    @Column(name = "description", nullable = false, length = 5000)
    private String description;

    @Column(name = "city")
    private String city;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToMany
    @JoinTable(
            name = "vacancy_specialties",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties;
}
