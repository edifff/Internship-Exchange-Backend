package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;

import java.util.UUID;

public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {
}
