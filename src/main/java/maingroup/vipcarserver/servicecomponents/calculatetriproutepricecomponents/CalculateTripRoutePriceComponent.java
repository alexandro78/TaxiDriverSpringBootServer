package maingroup.vipcarserver.servicecomponents.calculatetriproutepricecomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CityInsideOutsideTripRouteDistanceDto;
import maingroup.vipcarserver.dtos.pricedtos.BaseReadyPriceWithoutDemandCoefficientDto;
import maingroup.vipcarserver.dtos.pricedtos.PriceDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.servicecomponents.calculatetriproutedistancecomponents.CalculateInsideOutsideCityRouteDistance;
import maingroup.vipcarserver.servicecomponents.matchingbycarprioritycomponents.GetMaxMatchingByCarClassPriority;
import maingroup.vipcarserver.services.priceservices.PriceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CalculateTripRoutePriceComponent {

    private final GetMaxMatchingByCarClassPriority getMaxMatchingByCarClassPriority;
    private final CalculateInsideOutsideCityRouteDistance calculateInsideOutsideCityRouteDistance;
    private final PriceService priceService;


    public BaseReadyPriceWithoutDemandCoefficientDto calculateTripPriceWithoutCoefficient(Long riderId, SearchRiderDataDto rider, SearchFitDriversDataDto driver) {
        // Get the matching priority of the vehicle class
        int carClassPriority = getMaxMatchingByCarClassPriority.getMaxMatchingPriority(rider, driver);

        // Get the latest prices depending on the priority of the car class
        PriceDto priceDto = switch (carClassPriority) {
            case 1 -> priceService.getLatestElitePrices();
            case 2 -> priceService.getLatestPremiumPrices();
            case 3 -> priceService.getLatestLuxPrices();
            default -> priceService.getLatestComfortPrices();
        };

        // Calculate distances inside and outside the city.
        CityInsideOutsideTripRouteDistanceDto distanceResult = calculateInsideOutsideCityRouteDistance.calculateRouteDistances(riderId);

        // Convert distances to BigDecimal
        BigDecimal totalInsideDistance = BigDecimal.valueOf(distanceResult.getInsideRouteDistance());
        BigDecimal totalOutsideDistance = BigDecimal.valueOf(distanceResult.getOutsideRouteDistance());

        // Calculate the total cost of the trip
        BigDecimal totalTripPrice = priceDto.getBoardingFare()
                .add(priceDto.getCityKm().multiply(totalInsideDistance))
                .add(priceDto.getOutOfCityKm().multiply(totalOutsideDistance));

        // Create and fill DTO
        BaseReadyPriceWithoutDemandCoefficientDto resultDto = new BaseReadyPriceWithoutDemandCoefficientDto();
        resultDto.setBaseReadyPriceWithoutDemandCoefficient(totalTripPrice);
        resultDto.setCarClassPriorityIndex(carClassPriority);

        return resultDto;
    }
}
