package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.time.LocalDateTime;


@Entity
@Table(name = "files")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_data", columnDefinition = "bytea", nullable = false)
    private byte[] data;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}