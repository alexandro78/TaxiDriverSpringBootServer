package maingroup.vipcarserver.servicecomponents.searchlogiccomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.servicecomponents.searchlogiccomponents.searchtypes.*;
import maingroup.vipcarserver.services.DriverService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Option1DriverSearch {
    private final Option1SearchType option1SearchType;
    private final Option1SearchWType option1SearchWType;
    private final Option1SearchWideRadiusType option1SearchWideRadiusType;
    private final Option1SearchWideRType option1SearchWideRType;
    private final DriverService driverService;


    public FoundDriverDto searchOptionsDriver(Long userId, SearchRiderDataDto riderData) {
        FoundDriverDto closestDriverFound = null;

        // Check the 'coupe' field and call the appropriate method
        if (Boolean.TRUE.equals(riderData.getParam1())) {
            closestDriverFound = option1SearchType.searchClosestFitDriver(userId, riderData);
            if (closestDriverFound != null) {
                closestDriverFound.setSearchTypeCode(4);
            }
        } else {
            closestDriverFound = option1SearchWType.searchClosestFitDriver(userId, riderData);
            if (closestDriverFound != null) {
                closestDriverFound.setSearchTypeCode(3);
            }
        }

        // First check if a driver was found
        if (closestDriverFound != null) {
            System.out.println("driver found");

            Long driverId = closestDriverFound.getDriverId();
            Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
            closestDriverFound.setParam1(coupeStatus);

            return closestDriverFound;
        } else {

            if (Boolean.TRUE.equals(riderData.getParam1())) {
                closestDriverFound = option1SearchWideRadiusType.searchClosestFitDriver(userId, riderData);
                if (closestDriverFound != null) {
                    closestDriverFound.setSearchTypeCode(6);
                }
            } else {
                closestDriverFound = option1SearchWideRType.searchClosestFitDriver(userId, riderData);
                if (closestDriverFound != null) {
                    closestDriverFound.setSearchTypeCode(5);
                }
            }
        }


        if (closestDriverFound != null) {
            closestDriverFound.setFirstSearchNotFound(true);
            System.out.println("another driver found");

            Long driverId = closestDriverFound.getDriverId();
            Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
            closestDriverFound.setParam1(coupeStatus);

            return closestDriverFound;
        }
        return closestDriverFound;
    }
}
