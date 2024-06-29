package maingroup.vipcarserver.dtos.pricedtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderPriceFilterDto {
    private Long riderId;
    private BigDecimal riderPriceFilter;
}
