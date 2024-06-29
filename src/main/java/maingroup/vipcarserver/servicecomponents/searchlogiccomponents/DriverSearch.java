package maingroup.vipcarserver.servicecomponents.searchlogiccomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverSearch {
    private final DefaultDriverSearch defaultDriverSearch;
    private final Option1DriverSearch option1DriverSearch;
    private final Option2RideTripSearch option2RideTripSearch;

    public FoundDriverDto searchDriver(Long userId, SearchRiderDataDto riderData) {
        FoundDriverDto foundDriver;
        if (Boolean.FALSE.equals(riderData.getOption1()) && Boolean.FALSE.equals(riderData.getOption2())) {
            foundDriver = defaultDriverSearch.searchDefaultDriver(userId, riderData);
        } else if (Boolean.TRUE.equals(riderData.getOption1()) && Boolean.FALSE.equals(riderData.getOption2())) {
            foundDriver = option1DriverSearch.searchOptionsDriver(userId, riderData);
        } else {
            foundDriver = option2RideTripSearch.searchOption2Driver(userId, riderData);
        }
        return foundDriver;
    }
}
