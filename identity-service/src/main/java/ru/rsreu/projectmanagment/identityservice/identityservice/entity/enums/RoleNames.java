package ru.rsreu.projectmanagment.identityservice.identityservice.entity.enums;

public enum RoleNames {
    ROLE_STUDENT("student"), ROLE_EMPLOYER("employer"), ROLE_ADMIN("admin");

    private final String role;

    RoleNames(String role) {
        this.role=role;
    }

    public String getRole() {
        return role;
    }
}
