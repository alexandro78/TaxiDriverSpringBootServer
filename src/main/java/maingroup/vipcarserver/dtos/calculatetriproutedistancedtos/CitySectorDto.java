package maingroup.vipcarserver.dtos.calculatetriproutedistancedtos;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitySectorDto {
    private Long id;
    private String sectorName;

    @JsonRawValue
    private String polygonCoordinates;
}
