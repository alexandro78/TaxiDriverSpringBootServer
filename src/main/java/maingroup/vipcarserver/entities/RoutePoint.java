package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "route_points")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer orderIndex;

    @NotNull
    @Column(nullable = false)
    private Double pointLocationLatitude;

    @NotNull
    @Column(nullable = false)
    private Double pointLocationLongitude;

    @ManyToOne
    @JoinColumn(name = "current_trip_id")
    private CurrentTrip currentTrip;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "saved_route_id", nullable = false)
    private SavedRoute savedRoute;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;
}
