package ru.rsreu.projectmanagment.identityservice.identityservice.data.enums;

public enum Status {
    PENDING("PENDING"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED");

    private final String status;

     Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
