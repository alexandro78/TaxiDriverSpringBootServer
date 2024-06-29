package maingroup.vipcarserver.dtos.searchtripdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartCurrentRiderLocationDto {
    private Double startPointLocationLatitude;
    private Double startPointLocationLongitude;
}
