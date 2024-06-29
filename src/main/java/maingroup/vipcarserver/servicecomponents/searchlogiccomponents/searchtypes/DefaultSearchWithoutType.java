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
public class DefaultSearchWithoutType {
    private final BaseSearchLogic baseSearchLogic;
    private final DriverRepository driverRepository;

    public FoundDriverDto searchClosestFitDriver(Long userId, SearchRiderDataDto riderData) {
        // Lambda function to search for drivers without coupe drivers
        BiFunction<Double, Double, List<SearchFitDriversDataDto>> searchFunction = (latitude, longitude) -> {
            Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
            return driverRepository.findDriversWithoutParam1(latitude, longitude, maxCarArrivalRadius);
        };

        // Call the findClosestFitDriver method from BaseSearchLogicComponent
        return baseSearchLogic.findClosestFitDriver(userId, riderData, searchFunction);
    }
}