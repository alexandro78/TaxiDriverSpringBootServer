package maingroup.vipcarserver.dtos.pricedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private BigDecimal boardingFare;
    private BigDecimal cityKm;
    private BigDecimal outOfCityKm;
}
