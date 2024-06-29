package maingroup.vipcarserver.servicecomponents.matchingbycarprioritycomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.services.priceservices.PriceService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetMaxMatchingByCarClassPriority {
    private final PriceService priceService;

    //The method takes the highest priority that match for rider and driver
    public int getMaxMatchingPriority(SearchRiderDataDto rider, SearchFitDriversDataDto driver) {
        if (rider.getParam3() && driver.getElite()) return CarClassPriority.ELITE.getClassPriority();
        if (rider.getParam2() && driver.getPremium()) return CarClassPriority.PREMIUM.getClassPriority();
        if (rider.getLux() && driver.getLux()) return CarClassPriority.LUX.getClassPriority();
        return CarClassPriority.COMFORT.getClassPriority(); // Default case when none of the conditions worked
    }
}
