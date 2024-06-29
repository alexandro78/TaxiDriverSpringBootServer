package maingroup.vipcarserver.dtos.coefficientcountdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoefficientCountDto {
    private Integer nearbyTripSearchCount;
    private Integer nearbyAvailableDriversCount;
    private Double demandCoefficient;
}
