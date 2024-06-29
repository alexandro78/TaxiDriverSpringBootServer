package maingroup.vipcarserver.servicecomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.services.DriverService;
import maingroup.vipcarserver.services.RiderService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MainTestFindDriverComponent {

    private final RiderService riderService;
    private final DriverService driverService;
    private final FindClosestDriver findClosestDriver;

    public SearchFitDriversDataDto getClosestDriverForRider(Long riderId) {
        // Get rider data by user ID
        SearchRiderDataDto riderData = riderService.getSearchRiderDataByUserId(riderId);

        if (riderData == null) {
            throw new IllegalArgumentException("No rider found with ID: " + riderId);
        }

        // Get latitude and longitude from rider data
        double latitude = riderData.getStartPointLocationLatitude();
        double longitude = riderData.getStartPointLocationLongitude();

        // Find drivers without coupe
        List<SearchFitDriversDataDto> drivers = driverService.findDriversWithoutParam1(latitude, longitude);

        if (drivers.isEmpty()) {
            throw new IllegalArgumentException("No suitable drivers found for rider with ID: " + riderId);
        }

//         Find the closest driver
        return findClosestDriver.findClosestDriver(riderData, drivers);
    }
}