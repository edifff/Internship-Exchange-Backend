package ru.rsreu.projectmanagment.identityservice.identityservice.data.enums;

public enum RoleNames {
    ROLE_STUDENT("STUDENT"), ROLE_EMPLOYER("EMPLOYER"), ROLE_ADMIN("ADMIN");

    private final String role;

    RoleNames(String role) {
        this.role=role;
    }

    public String getRole() {
        return role;
    }
}
