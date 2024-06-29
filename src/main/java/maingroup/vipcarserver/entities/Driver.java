package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cars", "tripHistories", "currentTrips", "searchRides", "driverFilters", "user"})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean activeStatus = false;

    @NotNull
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal carArrivalRadius = BigDecimal.valueOf(1.5);

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'AVAILABLE'")
    private DriveStatus driveStatus = DriveStatus.FREE;

    @Column
    private Double currentLocationLatitude;

    @Column
    private Double currentLocationLongitude;

    @NotNull
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating = BigDecimal.valueOf(5.0);

    @NotNull
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int orderCount;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Option1> option1s = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripHistory> tripHistories = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrentTrip> currentTrips = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SearchRide> searchRides = new HashSet<>();

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DriverFilter> driverFilters = new HashSet<>();

    public enum DriveStatus {
        FREE,
        VEHICLE_DISPATCH,
        ARRIVED,
        AN_ROUTE,
        COMPLETED
    }
}





