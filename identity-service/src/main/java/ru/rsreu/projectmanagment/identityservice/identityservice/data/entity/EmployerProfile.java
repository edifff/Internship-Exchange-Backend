package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
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

    @Column(name="company_name", length = 160)
    private String companyName;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "website_link")
    @URL
    private String websiteLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_file_id")
    private FileEntity logo;


    @OneToMany(mappedBy = "employerId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> vacansies;
}
