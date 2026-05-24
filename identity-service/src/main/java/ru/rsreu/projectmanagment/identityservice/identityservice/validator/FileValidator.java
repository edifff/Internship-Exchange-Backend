package ru.rsreu.projectmanagment.identityservice.identityservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@Component
public class FileValidator {

    private static final long IMAGE_MAX_SIZE = 5 * 1024 * 1024;
    private static final long RESUME_MAX_SIZE = 15 * 1024 * 1024;

    private static final Set<String> IMAGE_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final Set<String> RESUME_TYPES = Set.of(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public void validateImage(MultipartFile file) throws BadRequestException {
        validate(file, IMAGE_TYPES, IMAGE_MAX_SIZE);
    }

    public void validateResume(MultipartFile file) throws BadRequestException {
        validate(file, RESUME_TYPES, RESUME_MAX_SIZE);
    }

    private void validate(
            MultipartFile file,
            Set<String> allowedTypes,
            long maxSize
    ) throws BadRequestException {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        if (!allowedTypes.contains(file.getContentType())) {
            throw new BadRequestException("Unsupported mime type");
        }

        if (file.getSize() > maxSize) {
            throw new BadRequestException("File too large");
        }
    }
}
