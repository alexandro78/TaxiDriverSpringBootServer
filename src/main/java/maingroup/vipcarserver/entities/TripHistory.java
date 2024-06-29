package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trip_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @NotNull
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime startTime;

    @NotNull
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime endTime;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal price = BigDecimal.valueOf(0.00);

    @NotNull
    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION DEFAULT 0")
    private Double distance = 0.0;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String carModel;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String plateNumber;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean param1 = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'COMFORT'")
    private CarClass carClass = CarClass.COMFORT;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'CASH'")
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'NONE'")
    private TripHistory.Op1Status op1Status = Op1Status.NONE;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'COMPLETE'")
    private OrderStatus orderStatus = OrderStatus.COMPLETE;

    public enum CarClass {
        COMFORT,
        LUX,
        PREMIUM,
        ELITE
    }

    public enum PaymentMethod {
        CASH,
        CARD,
        OPTION2
    }

    public enum Op1Status {
        NONE,
        CHAT_CREATED,
        OPTION2
    }

    public enum OrderStatus {
        COMPLETE,
        DRIVER_CANCEL,
        RIDER_CANCEL,
        DRIVER_REJECT,
        RIDER_REJECT
    }

    @OneToMany(mappedBy = "tripHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripHistoryRoutePoint> routePoints = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;
}
