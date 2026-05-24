package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.FileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.FileService;

import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Files")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка изображения")
    public FileDTO uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        return fileService.uploadImage(file, authentication);
    }

    @PostMapping(value = "/resumes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка резюме")
    public FileDTO uploadResume(@RequestParam("file") MultipartFile file, Authentication authentication) {
        return fileService.uploadResume(file, authentication);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение файла")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
        return fileService.download(id);
    }
}
