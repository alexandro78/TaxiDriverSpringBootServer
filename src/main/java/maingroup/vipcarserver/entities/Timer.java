package maingroup.vipcarserver.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "options_timers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(25) DEFAULT 'AVAILABLE'")
    private Status status = Status.TIMER_ACTIVATED;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Column(nullable = false)
    private int deactivationRiderCode;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Column(nullable = false)
    private int deactivationDriverCode;

    @OneToOne
    @JoinColumn(name = "option1_id", nullable = false)
    private Option1 option1;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;


    public enum Status {
        TIMER_ACTIVATED,
        TIMER_DEACTIVATED
    }
}
