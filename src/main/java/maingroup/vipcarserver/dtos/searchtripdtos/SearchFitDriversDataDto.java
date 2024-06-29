package maingroup.vipcarserver.dtos.searchtripdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFitDriversDataDto {
    private Long userId;
    private Long driverId;
    private BigDecimal priceFilter;
    private Boolean comfort;
    private Boolean lux;
    private Boolean premium;
    private Boolean elite;
    private Double currentLocationLatitude;
    private Double currentLocationLongitude;
    private BigDecimal carArrivalRadius;
}
