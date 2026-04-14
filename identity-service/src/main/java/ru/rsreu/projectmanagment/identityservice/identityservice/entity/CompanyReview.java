package ru.rsreu.projectmanagment.identityservice.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "company_reviews")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerProfile employer;

    private Short rating;

    private String comment;

    private LocalDate createdAt;
}
