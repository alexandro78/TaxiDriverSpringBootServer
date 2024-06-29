package maingroup.vipcarserver.dtos.founddriverdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundDriverDto {
    private Long riderId;
    private Long driverId;
    private Long savedRouteId;
    private int carClassPriorityIndex;
    private BigDecimal readyGoPrice;
    private Boolean option1;
    private Boolean option2;
    private Double demandCoefficient;
    private Double entireRouteDistance;
    private Boolean firstSearchNotFound;
    private Boolean param1;
    private int searchTypeCode;
}
