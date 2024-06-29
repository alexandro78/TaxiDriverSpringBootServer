package maingroup.vipcarserver.dtos.calculatetriproutedistancedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInsideOutsideTripRouteDistanceDto {
    private Double insideRouteDistance;
    private Double outsideRouteDistance;
}
