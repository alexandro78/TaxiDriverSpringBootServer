package maingroup.vipcarserver.dtos.driverhomescreendtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocketHomeScreenRequestDto {
    private String driverLocationLatitude;
    private String driverLocationLongitude;
}

