package ru.rsreu.projectmanagment.identityservice.identityservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Component
public class FileStorageConfig {

    @Value("${app.file.max-size-bytes}")
    private long maxSizeBytes;

    public Set<String> imageMimeTypes() {

        return Set.of(
                "image/jpeg",
                "image/png",
                "image/webp"
        );
    }

    public Set<String> resumeMimeTypes() {

        return Set.of(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
    }
}
