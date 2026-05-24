package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.FileType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FileDTO {

    private UUID id;

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private FileType fileType;

    private LocalDateTime createdAt;

    private String downloadUrl;
}
