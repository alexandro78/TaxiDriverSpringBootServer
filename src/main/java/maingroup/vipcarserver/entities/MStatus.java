package maingroup.vipcarserver.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "text_message", columnDefinition = "TEXT")
    private String textMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'sent'")
    private MessageStatus messageStatus = MessageStatus.SENT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp without time zone")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "timestamp without time zone")
    private LocalDateTime deletedAt;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    public enum MessageStatus {
        SENT, DELIVERED, READ
    }

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
//Completed//