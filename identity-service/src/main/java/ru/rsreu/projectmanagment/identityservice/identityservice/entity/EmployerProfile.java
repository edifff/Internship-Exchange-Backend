package ru.rsreu.projectmanagment.identityservice.identityservice.entity;


import jakarta.persistence.*;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Table(name = "employer_profile")
public class EmployerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="company_name", nullable = false, length = 160)
    private String companyName;
    @Column(name = "description", nullable = false, length = 5000)
    private String description;
    @Column(name = "wedsite_link")
    private URL websiteLink;
    @Column(name="file_id", nullable = false)
    private UUID file_id;

    @OneToMany(mappedBy = "employer_profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacansy> vacansies;
}
