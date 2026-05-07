package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
