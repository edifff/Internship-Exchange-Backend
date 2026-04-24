package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Resume;

import java.time.Year;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfileDTO{
    private String fullName;
    private Integer graduationYear;
    private UUID avatarUrl;
    private UUID resume;
}
