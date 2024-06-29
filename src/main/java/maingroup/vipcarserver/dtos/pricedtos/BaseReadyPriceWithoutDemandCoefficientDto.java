package maingroup.vipcarserver.dtos.pricedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseReadyPriceWithoutDemandCoefficientDto {
    private BigDecimal baseReadyPriceWithoutDemandCoefficient;
    private int carClassPriorityIndex;
}
