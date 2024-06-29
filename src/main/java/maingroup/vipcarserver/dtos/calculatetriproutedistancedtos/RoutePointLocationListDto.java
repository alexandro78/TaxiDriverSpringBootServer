package maingroup.vipcarserver.dtos.calculatetriproutedistancedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePointLocationListDto {
    private Long id;
    private Long driverId;
    private Long savedRouteId;
    private Integer orderIndex;
    private Double pointLocationLatitude;
    private Double pointLocationLongitude;
    private LocalDateTime timestamp;
}
