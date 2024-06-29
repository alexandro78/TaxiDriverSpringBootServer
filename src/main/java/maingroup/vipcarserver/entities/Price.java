package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "comfort_boarding_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal comfortBoardingFare = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "comfort_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal comfortCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "comfort_out_of_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal comfortOutOfCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "lux_boarding_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal luxBoardingFare = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "lux_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal luxCityKm = BigDecimal.valueOf(0.0);


    @NotNull
    @Column(name = "lux_out_of_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal luxOutOfCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "premium_boarding_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal premiumBoardingFare = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "premium_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal premiumCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "premium_out_of_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal premiumOutOfCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "elite_boarding_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal eliteBoardingFare = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "elite_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal eliteCityKm = BigDecimal.valueOf(0.0);

    @NotNull
    @Column(name = "elite_out_of_city_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal eliteOutOfCityKm = BigDecimal.valueOf(0.0);

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
