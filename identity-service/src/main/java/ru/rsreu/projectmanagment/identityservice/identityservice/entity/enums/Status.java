package ru.rsreu.projectmanagment.identityservice.identityservice.entity.enums;

public enum Status {
    PENDING("pending"), REVIEWED("reviewed"), ACCEPTED("accepted"), REJECTED("rejected");

    private final String status;

     Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
