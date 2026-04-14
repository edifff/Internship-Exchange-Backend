package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.MessageType;
import com.fasterxml.jackson.databind.JsonNode;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
