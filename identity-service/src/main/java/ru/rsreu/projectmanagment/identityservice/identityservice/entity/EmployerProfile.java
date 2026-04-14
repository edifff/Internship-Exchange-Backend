package ru.rsreu.projectmanagment.identityservice.identityservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employer_profile")
public class EmployerProfile {
    @Id
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="company_name", nullable = false, length = 160)
    private String companyName;

    @Column(name = "description", nullable = false, length = 5000)
    private String description;

    @Column(name = "wedsite_link")
    @URL
    private java.net.URL websiteLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_file_id")
    private FileEntity logo;


    @OneToMany(mappedBy = "employer_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> vacansies;
}
