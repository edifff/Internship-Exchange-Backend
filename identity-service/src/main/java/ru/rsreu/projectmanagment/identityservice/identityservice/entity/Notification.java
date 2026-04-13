package ru.rsreu.projectmanagment.identityservice.identityservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.rsreu.projectmanagment.identityservice.identityservice.entity.enums.MessageType;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.UUID;

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "massage_type")
    private MessageType messageType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb" )
    private JsonNode payload;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name="createdAt")
    private LocalDate createdAt;
}
