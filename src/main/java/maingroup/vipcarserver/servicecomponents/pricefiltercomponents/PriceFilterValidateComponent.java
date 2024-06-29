package maingroup.vipcarserver.servicecomponents.pricefiltercomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.pricedtos.DriverPriceFilterDto;
import maingroup.vipcarserver.dtos.pricedtos.RiderPriceFilterDto;
import maingroup.vipcarserver.services.priceservices.DriverPriceFilterService;
import maingroup.vipcarserver.services.priceservices.RiderPriceFilterService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PriceFilterValidateComponent {
    private final DriverPriceFilterService driverPriceFilterService;
    private final RiderPriceFilterService riderPriceFilterService;

    private boolean checkDriverPriceFilter(Long driverId, BigDecimal readyGoPrice) {
        DriverPriceFilterDto driverPriceFilterDto = driverPriceFilterService.getDriverPriceFilterByDriverId(driverId);
        if (driverPriceFilterDto == null || driverPriceFilterDto.getDriverPriceFilter() == null || driverPriceFilterDto.getDriverPriceFilter().compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return readyGoPrice.compareTo(driverPriceFilterDto.getDriverPriceFilter()) >= 0;
    }

    private boolean checkRiderPriceFilter(Long riderId, BigDecimal readyGoPrice) {
        RiderPriceFilterDto riderPriceFilterDto = riderPriceFilterService.getRiderPriceFilterByRiderId(riderId);
        if (riderPriceFilterDto == null || riderPriceFilterDto.getRiderPriceFilter() == null || riderPriceFilterDto.getRiderPriceFilter().compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return readyGoPrice.compareTo(riderPriceFilterDto.getRiderPriceFilter()) <= 0;
    }

    public boolean validateTripPrice(Long driverId, Long riderId, BigDecimal readyGoPrice) {
        boolean isDriverPriceValid = checkDriverPriceFilter(driverId, readyGoPrice);
        boolean isRiderPriceValid = checkRiderPriceFilter(riderId, readyGoPrice);

        return isDriverPriceValid && isRiderPriceValid;
    }
}
