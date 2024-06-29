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
@Table(name = "current_trips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'NONE'")
    private CurrentTrip.Op1Status op1Status = Op1Status.NONE;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal price = BigDecimal.valueOf(0.00);

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'CASH'")
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'CarArrival'")
    private DriveStatus driveStatus = DriveStatus.CAR_ARRIVAL;

    @NotNull
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int waitingTimer = 0;

    @NotNull
    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION DEFAULT 0")
    private double distance = 0.0;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String carModel;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String plateNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'COMFORT'")
    private CarClass carClass = CarClass.COMFORT;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean param1 = false;

    @Size(max = 255)
    @Column(length = 255)
    private String comment;

    public enum Op1Status {
        NONE,
        CHAT_CREATED,
        OPTION2
    }

    public enum PaymentMethod {
        CASH,
        CARD,
        OPTION2
    }

    public enum DriveStatus {
        CAR_ARRIVAL,
        START_LOCATION,
        EN_ROUTE,
        COMPLETED
    }



    public enum CarClass {
        COMFORT,
        LUX,
        PREMIUM,
        ELITE
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @OneToMany(mappedBy = "currentTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoutePoint> routePoints = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime startTripTimer;

    @Column
    private LocalDateTime endTripTimer;
}