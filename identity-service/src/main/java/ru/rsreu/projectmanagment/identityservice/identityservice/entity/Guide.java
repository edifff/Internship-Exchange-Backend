package ru.rsreu.projectmanagment.identityservice.identityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Table(name = "guide")
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "content", nullable = false)
    private Date created_at;
}
