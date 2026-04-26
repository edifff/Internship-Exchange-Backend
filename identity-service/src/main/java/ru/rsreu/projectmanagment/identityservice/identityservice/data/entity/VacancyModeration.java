package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vacancy_moderation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancyModeration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    private String decision;

    private String comment;

    private LocalDate createdAt;
}