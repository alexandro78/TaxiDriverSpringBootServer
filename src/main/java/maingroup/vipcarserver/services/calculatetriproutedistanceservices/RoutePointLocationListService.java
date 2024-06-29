package maingroup.vipcarserver.services.calculatetriproutedistanceservices;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.RoutePointLocationListDto;
import maingroup.vipcarserver.repositories.calculatetriproutedistance.RoutePointLocationListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutePointLocationListService {

    private final RoutePointLocationListRepository routePointLocationListRepository;

    public List<RoutePointLocationListDto> getAllPointLocationRouteListByRiderId(Long riderId) {
        return routePointLocationListRepository.getAllPointLocationRouteListByRiderId(riderId);
    }
}
