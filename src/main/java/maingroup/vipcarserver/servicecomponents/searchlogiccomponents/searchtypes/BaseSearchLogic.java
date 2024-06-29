package maingroup.vipcarserver.servicecomponents.searchlogiccomponents.searchtypes;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CityInsideOutsideTripRouteDistanceDto;
import maingroup.vipcarserver.dtos.coefficientcountdto.CoefficientCountDto;
import maingroup.vipcarserver.dtos.founddriverdto.SettingOptionsDto;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.pricedtos.BaseReadyPriceWithoutDemandCoefficientDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.servicecomponents.FindClosestDriver;
import maingroup.vipcarserver.servicecomponents.calculatetriproutedistancecomponents.CalculateInsideOutsideCityRouteDistance;
import maingroup.vipcarserver.servicecomponents.calculatetriproutepricecomponents.CalculateTripRoutePriceComponent;
import maingroup.vipcarserver.servicecomponents.matchingbycarprioritycomponents.DriversFilterByRiderCarClasses;
import maingroup.vipcarserver.servicecomponents.searchlogiccomponents.exceptions.RiderDataNotFoundException;
import maingroup.vipcarserver.services.DriverService;
import maingroup.vipcarserver.services.RiderService;
import maingroup.vipcarserver.services.tripdemandcoefficientservices.TripDemandCoefficientService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class BaseSearchLogic {
    private final RiderService riderService;
    private final DriverService driverService;
    private final DriversFilterByRiderCarClasses driversFilterByRiderCarClasses;
    private final FindClosestDriver findClosestDriver;
    private final CalculateTripRoutePriceComponent calculateTripRoutePriceComponent;
    private final TripDemandCoefficientService tripDemandCoefficientService;
    private final CalculateInsideOutsideCityRouteDistance calculateInsideOutsideCityRouteDistance;

    public FoundDriverDto findClosestFitDriver(Long userId, SearchRiderDataDto riderData,
                                               BiFunction<Double, Double, List<SearchFitDriversDataDto>> searchFunction) {
        if (riderData == null) {
            throw new RiderDataNotFoundException("Search error: Rider data not found");
        }

        List<SearchFitDriversDataDto> fitDrivers;
        try {
            fitDrivers = driverService.findDrivers(searchFunction,
                    riderData.getStartPointLocationLatitude(),
                    riderData.getStartPointLocationLongitude());
        } catch (Exception e) {
            return null;
        }

        if (fitDrivers == null || fitDrivers.isEmpty()) {
            return null;
        }

        List<SearchFitDriversDataDto> filteredDrivers = driversFilterByRiderCarClasses.filterDriversByRiderClasses(riderData, fitDrivers);

        if (filteredDrivers.isEmpty()) {
            return null;
        }

        SearchFitDriversDataDto closestDriver = findClosestDriver.findClosestDriver(riderData, filteredDrivers);

        if (closestDriver == null) {
            return null;
        }


        double tripPriceWithDemandCoefficient;

        CoefficientCountDto coefficientCountDto = tripDemandCoefficientService.getCurrentTripDemandCoefficient(riderData.getRiderId());

        if (Boolean.TRUE.equals(riderData.getOption2())) {
            tripPriceWithDemandCoefficient = 0;
        } else {

            BaseReadyPriceWithoutDemandCoefficientDto tripPriceWithoutDemandCoefficient = calculateTripRoutePriceComponent.calculateTripPriceWithoutCoefficient(riderData.getRiderId(), riderData, closestDriver);

            tripPriceWithDemandCoefficient = tripPriceWithoutDemandCoefficient.getBaseReadyPriceWithoutDemandCoefficient().doubleValue() * coefficientCountDto.getDemandCoefficient();
        }

        CityInsideOutsideTripRouteDistanceDto distanceResult = calculateInsideOutsideCityRouteDistance.calculateRouteDistances(riderData.getRiderId());

        Double entireRouteDistance = distanceResult.getInsideRouteDistance() + distanceResult.getOutsideRouteDistance();

        Long savedRoute = riderService.getSavedRouteByRiderId(riderData.getRiderId());

        SettingOptionsDto OptionsSetting = riderService.getOptionsSettingByUserId(userId);

        return new FoundDriverDto(
                riderData.getRiderId(),
                closestDriver.getDriverId(),
                savedRoute,
                (int) tripPriceWithDemandCoefficient,
                BigDecimal.valueOf(tripPriceWithDemandCoefficient),
                OptionsSetting.getOption1(),
                OptionsSetting.getOption2(),
                coefficientCountDto.getDemandCoefficient(),
                entireRouteDistance,
                false,
                false,
                0
        );
    }
}
