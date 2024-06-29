package maingroup.vipcarserver.servicecomponents.searchlogiccomponents.searchtypes;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.repositories.DriverRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class Option1SearchWType {
    private final BaseSearchLogic baseSearchLogic;
    private final DriverRepository driverRepository;

    public FoundDriverDto searchClosestFitDriver(Long userId, SearchRiderDataDto riderData) {

        BiFunction<Double, Double, List<SearchFitDriversDataDto>> searchFunction = (latitude, longitude) -> {
            Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
            return driverRepository.findDriversWithOption1WithoutParam1(latitude, longitude, maxCarArrivalRadius);
        };


        return baseSearchLogic.findClosestFitDriver(userId, riderData, searchFunction);
    }
}
