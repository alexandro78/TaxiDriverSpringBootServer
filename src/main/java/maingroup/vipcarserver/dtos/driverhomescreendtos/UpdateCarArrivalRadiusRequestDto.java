package maingroup.vipcarserver.dtos.driverhomescreendtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarArrivalRadiusRequestDto {
    private BigDecimal carArrivalRadius;
}
