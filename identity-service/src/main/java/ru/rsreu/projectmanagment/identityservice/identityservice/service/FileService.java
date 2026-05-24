package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.rsreu.projectmanagment.identityservice.identityservice.config.FileStorageConfig;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.FileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.FileType;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.FileEntityRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.UserRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.ValidationException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.FileMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileEntityRepository fileRepository;
    private final UserRepository userRepository;
    private final FileMapper fileMapper;
    private final FileStorageConfig storageConfig;

    @Transactional
    public FileDTO uploadImage(MultipartFile file, Authentication authentication) {

        validate(file, storageConfig.imageMimeTypes());

        User user = getCurrentUser(authentication);

        FileEntity entity = save(file, user, FileType.IMAGE);

        return fileMapper.toDTO(entity);
    }

    @Transactional
    public FileDTO uploadResume(MultipartFile file, Authentication authentication) {

        validate(file, storageConfig.resumeMimeTypes());

        User user = getCurrentUser(authentication);

        FileEntity entity = save(file, user, FileType.RESUME);

        return fileMapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> download(
            UUID id
    ) {

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Файл не найден"));

        org.springframework.http.HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.parseMediaType(file.getMimeType())
        );

        headers.setContentLength(file.getFileSize());

        if (file.getFileType() == FileType.IMAGE) {

            headers.setContentDisposition(ContentDisposition.inline()
                            .filename(file.getFileName())
                            .build());

        } else {

            headers.setContentDisposition(ContentDisposition.attachment()
                            .filename(file.getFileName())
                            .build());
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(file.getData());
    }

    @Transactional(readOnly = true)
    public FileEntity getOwnedFile(UUID fileId, UUID ownerId) {

        return fileRepository.findByIdAndOwnerId(fileId, ownerId)
                .orElseThrow(() -> new NotFoundException("Файл не найден"));
    }

    private FileEntity save(MultipartFile multipartFile, User owner, FileType type) {

        try {

            FileEntity entity = FileEntity.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .mimeType(multipartFile.getContentType())
                    .fileSize(multipartFile.getSize())
                    .fileType(type)
                    .data(multipartFile.getBytes())
                    .owner(owner)
                    .createdAt(LocalDateTime.now())
                    .build();

            return fileRepository.save(entity);

        } catch (IOException e) {
            throw new ValidationException("Ошибка чтения файла");
        }
    }

    private void validate(MultipartFile file, Set<String> allowedTypes) {

        if (file.isEmpty()) {
            throw new ValidationException("Файл пуст");
        }

        if (file.getSize() > storageConfig.getMaxSizeBytes()) {
            throw new ValidationException("Превышен максимальный размер файла");
        }

        if (!allowedTypes.contains(file.getContentType())) {
            throw new ValidationException("Недопустимый тип файла");
        }
    }

    private User getCurrentUser(Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }
}