package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "saved_routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rider_id", nullable = false, unique = true)
    private Rider rider;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean parametr1 = false;

    @OneToMany(mappedBy = "savedRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoutePoint> routePoints = new HashSet<>();

    @OneToMany(mappedBy = "savedRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SearchRide> searchRides = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;
}
