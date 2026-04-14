package ru.rsreu.projectmanagment.identityservice.identityservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import jakarta.validation.constraints.Pattern;


import java.time.Year;
import java.util.UUID;

@Entity
@Table(name = "student_profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {
    @Id
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Pattern(regexp = "^[^0-9]*$", message = "Цифры запрещены")
    private String fullName;

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
    private Resume resume;

    @Min(value = 1950, message = "Необходимо ввести год не позднее 1950")
    private Year graduationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_file_id")
    private FileEntity avatar;
}
