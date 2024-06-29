package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_rides")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean parametr1 = false;

    @NotNull
    @Column(nullable = false)
    private Double startPointLocationLatitude;

    @NotNull
    @Column(nullable = false)
    private Double startPointLocationLongitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'NEW'")
    private Status driveStatus = Status.NEW;

    public enum Status {
       NEW,
        SEARCH,
        CAR_FOUND,
        NO_CAR_FOUND
    }

    @ManyToOne
    @JoinColumn(name = "saved_route_id", nullable = false)
    private SavedRoute savedRoute;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;
}

