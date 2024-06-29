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
@Table(name = "riders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"savedRoute", "user", "riderFilters", "tripHistories", "currentTrips", "searchRides", "riderCarClass"})
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int tripCount;

    @NotNull
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating = BigDecimal.valueOf(5.0);

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @OneToOne(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private SavedRoute savedRoute;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Option1> option1s = new HashSet<>();

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RiderFilter> riderFilters = new HashSet<>();

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripHistory> tripHistories = new HashSet<>();

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrentTrip> currentTrips = new HashSet<>();

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SearchRide> searchRides = new HashSet<>();

    @OneToOne(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
    private RiderCarClass riderCarClass;
}
