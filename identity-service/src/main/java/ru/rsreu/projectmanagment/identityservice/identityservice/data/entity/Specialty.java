package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "specialties")
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private boolean isActive;

}