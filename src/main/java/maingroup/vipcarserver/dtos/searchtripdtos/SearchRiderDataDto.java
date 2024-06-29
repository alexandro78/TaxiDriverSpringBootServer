package maingroup.vipcarserver.dtos.searchtripdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRiderDataDto {
    private Long userId;
    private Long riderId;
    private Boolean param1;
    private Boolean comfort;
    private Boolean lux;
    private Boolean param2;
    private Boolean param3;
    private Boolean option1;
    private Boolean option2;
    private Double startPointLocationLatitude;
    private Double startPointLocationLongitude;
    private BigDecimal priceFilter;
}
