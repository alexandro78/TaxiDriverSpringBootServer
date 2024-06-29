package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "option1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(25) DEFAULT 'AVAILABLE'")
    private Status status = Status.SCHEDULED;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @OneToOne(mappedBy = "option1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Timer timer;

    public enum Status {
        SCHEDULED,
        TIMER_ACTIVATED,
        TIMER_DEACTIVATED,
    }
}
