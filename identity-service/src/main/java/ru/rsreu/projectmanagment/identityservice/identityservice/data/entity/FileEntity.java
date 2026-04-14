package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String fileKey;
    private String fileUrl;
    private String fileName;
    private String mimeType;
    private Integer fileSize;

    private LocalDate createdAt;
}