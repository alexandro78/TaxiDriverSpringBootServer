package maingroup.vipcarserver.controllers.testcontrollers;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CityInsideOutsideTripRouteDistanceDto;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.RoutePointLocationListDto;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CitySectorDto;
import maingroup.vipcarserver.dtos.coefficientcountdto.CoefficientCountDto;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.pricedtos.BaseReadyPriceWithoutDemandCoefficientDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.servicecomponents.calculatetriproutedistancecomponents.CalculateInsideOutsideCityRouteDistance;
import maingroup.vipcarserver.servicecomponents.calculatetriproutepricecomponents.CalculateTripRoutePriceComponent;
import maingroup.vipcarserver.servicecomponents.pricefiltercomponents.PriceFilterValidateComponent;
import maingroup.vipcarserver.servicecomponents.searchlogiccomponents.DriverSearch;
import maingroup.vipcarserver.services.DriverService;
import maingroup.vipcarserver.services.RiderService;
import maingroup.vipcarserver.services.calculatetriproutedistanceservices.CitySectorService;
import maingroup.vipcarserver.services.calculatetriproutedistanceservices.RoutePointLocationListService;
import maingroup.vipcarserver.services.tripdemandcoefficientservices.TripDemandCoefficientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataExtractionController {
    private final RoutePointLocationListService routePointLocationListService;
    private final CitySectorService citySectorService;
    private final CalculateInsideOutsideCityRouteDistance calculateInsideOutsideCityRouteDistance;
    private final TripDemandCoefficientService tripDemandCoefficientService;
    private final CalculateTripRoutePriceComponent calculateTripRoutePriceComponent;
    private final PriceFilterValidateComponent priceFilterValidateComponent;
    private final RiderService riderService;
    private final DriverSearch driverSearch;
    private final DriverService driverService;

    @GetMapping("/first")
    public CitySectorDto getFirstCitySector2() {
        return citySectorService.getFirstCitySector();
    }

    @GetMapping("/routes/{riderId}")
    public List<RoutePointLocationListDto> getRoutePointsByRiderId(@PathVariable Long riderId) {
        return routePointLocationListService.getAllPointLocationRouteListByRiderId(riderId);
    }

    @GetMapping("/route-distance/{riderId}")
    public CityInsideOutsideTripRouteDistanceDto getRouteDistances(@PathVariable Long riderId) {
        return calculateInsideOutsideCityRouteDistance.calculateRouteDistances(riderId);
    }


    @GetMapping("/get-current-trip-number/{riderId}")
    public CoefficientCountDto findNearbySearchRides(@PathVariable Long riderId) {
        return tripDemandCoefficientService.getCurrentTripDemandCoefficient(riderId);
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<BaseReadyPriceWithoutDemandCoefficientDto> calculateTripPrice(@RequestParam Long riderId,
                                                                                        @RequestBody SearchRiderDataDto rider,
                                                                                        @RequestBody SearchFitDriversDataDto driver) {
        BaseReadyPriceWithoutDemandCoefficientDto tripPriceDto = calculateTripRoutePriceComponent.calculateTripPriceWithoutCoefficient(riderId, rider, driver);
        return ResponseEntity.ok(tripPriceDto);
    }

    @GetMapping("/validate-price")
    public ResponseEntity<Boolean> validateTripPrice(
            @RequestParam Long driverId,
            @RequestParam Long riderId) {

        BigDecimal readyGoPrice = new BigDecimal("240.00");
        boolean isValid = priceFilterValidateComponent.validateTripPrice(driverId, riderId, readyGoPrice);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/fit-drivers/{userId}")
    public ResponseEntity<?> getClosestFitDriver(@PathVariable Long userId) {
        SearchRiderDataDto riderData = riderService.getSearchRiderDataByUserId(userId);

        FoundDriverDto foundDriver = driverSearch.searchDriver(userId, riderData);

        return ResponseEntity.ok(foundDriver);
    }


    @GetMapping("/coupe-status/{driverId}")
    public ResponseEntity<Boolean> getParam1StatusForActiveCar(@PathVariable Long driverId) {
        Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
        return ResponseEntity.ok(coupeStatus);
    }
}