package ru.rsreu.projectmanagment.identityservice.identityservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.RoleNames;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleNames name);
}
