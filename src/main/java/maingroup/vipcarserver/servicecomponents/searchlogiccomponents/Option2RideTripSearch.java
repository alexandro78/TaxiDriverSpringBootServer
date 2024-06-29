package maingroup.vipcarserver.servicecomponents.searchlogiccomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.founddriverdto.FoundDriverDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.servicecomponents.searchlogiccomponents.searchtypes.*;
import maingroup.vipcarserver.services.DriverService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Option2RideTripSearch {
    private final Option1SearchType option1SearchType;
    private final Option1SearchWType option1SearchWType;
    private final Option1SearchWideRadiusType option1SearchWideRadiusType;
    private final Option1SearchWideRType option1SearchWideRType;
    private final Option2SearchWideRadiusWType option2SearchWideRadiusWType;
    private final Option2SearchWideRType option2SearchWideRType;
    private final DriverService driverService;


    public FoundDriverDto searchOption2Driver(Long userId, SearchRiderDataDto riderData) {
        FoundDriverDto closestDriverFound = null;

        if (Boolean.TRUE.equals(riderData.getParam1())) {
            closestDriverFound = option2SearchWideRType.searchClosestFitDriver(userId, riderData);
            if (closestDriverFound != null) {
                closestDriverFound.setSearchTypeCode(8);
            }
        } else {
            closestDriverFound = option2SearchWideRadiusWType.searchClosestFitDriver(userId, riderData);
            if (closestDriverFound != null) {
                closestDriverFound.setSearchTypeCode(7);
            }
        }


        if (closestDriverFound != null) {
            System.out.println("driver found");

            Long driverId = closestDriverFound.getDriverId();
            Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
            closestDriverFound.setParam1(coupeStatus);

            return closestDriverFound;
        } else {

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
        }

        // Second check if a driver was found
        if (closestDriverFound != null) {
            closestDriverFound.setFirstSearchNotFound(true);
            System.out.println("another driver found");

            Long driverId = closestDriverFound.getDriverId();
            Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
            closestDriverFound.setParam1(coupeStatus);

            return closestDriverFound;
        }

        // If no driver was found in the second search, call the wide radius search method
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

        // Last check if a driver was found
        if (closestDriverFound != null) {
            closestDriverFound.setFirstSearchNotFound(true);
            System.out.println("another driver found");

            Long driverId = closestDriverFound.getDriverId();
            Boolean coupeStatus = driverService.getParam1StatusForActiveCar(driverId);
            closestDriverFound.setParam1(coupeStatus);
        }

        return closestDriverFound;
    }
}
