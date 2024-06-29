package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip_history_route_points")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripHistoryRoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_history_id", nullable = false)
    private TripHistory tripHistory;

    @NotNull
    @Column(nullable = false)
    private Integer orderIndex;

    @NotNull
    @Column(nullable = false)
    private Double pointLocationLatitude;

    @NotNull
    @Column(nullable = false)
    private Double pointLocationLongitude;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;
}
//Completed//
