package maingroup.vipcarserver.servicecomponents;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class FindClosestDriver {

    public SearchFitDriversDataDto findClosestDriver(SearchRiderDataDto riderData, List<SearchFitDriversDataDto> drivers) {
        SearchFitDriversDataDto closestDriverDto = null;
        double minDistance = Double.MAX_VALUE;
        List<SearchFitDriversDataDto> closestRandomDrivers = new ArrayList<>();

        double riderStartPointLocationLatitude = riderData.getStartPointLocationLatitude();
        double riderStartPointLocationLongitude = riderData.getStartPointLocationLongitude();

        // Find the closest driver and the minimum distance
        for (SearchFitDriversDataDto driverDto : drivers) {
            double distance = calculateDistance(
                    riderStartPointLocationLatitude, riderStartPointLocationLongitude,
                    driverDto.getCurrentLocationLatitude(), driverDto.getCurrentLocationLongitude()
            );

            distance = Math.ceil(distance * 10) / 10.0;
            // Convert carArrivalRadius to double type
            double carArrivalRadius = driverDto.getCarArrivalRadius().doubleValue();

            if (distance < minDistance && distance <= carArrivalRadius) {
                minDistance = distance;
                closestDriverDto = driverDto;
            }
        }

        // Add to the list all drivers who are within 10% of the minimum distance
        double threshold = minDistance * 1.1; // 10% more than the minimum distance
        for (SearchFitDriversDataDto driverDto : drivers) {
            double distance = calculateDistance(
                    riderStartPointLocationLatitude, riderStartPointLocationLongitude,
                    driverDto.getCurrentLocationLatitude(), driverDto.getCurrentLocationLongitude()
            );

            // Convert carArrivalRadius to double type
            double carArrivalRadius = driverDto.getCarArrivalRadius().doubleValue();

            if (distance <= threshold && distance <= carArrivalRadius) {
                closestRandomDrivers.add(driverDto);
            }
        }


        // Select a random driver from the list of suitable candidates
        if (!closestRandomDrivers.isEmpty()) {
            Random random = new Random();
            System.out.println("closestRandomDrivers.isEmpty: false!!!888" + closestRandomDrivers);
            return closestRandomDrivers.get(random.nextInt(closestRandomDrivers.size()));
        }
        return closestDriverDto;  // Return the closest one if there are no suitable ones within the threshold
    }

    private double calculateDistance(double riderStartPointLatitude, double riderStartPointLongitude, double driverCurrentPointLocationLatitude, double driverCurrentPointLocationLongitude) {
        // Convert latitude and longitude to kilometers using the approximate value of one degree length.
        double deltaLat = driverCurrentPointLocationLatitude - riderStartPointLatitude;
        double deltaLon = driverCurrentPointLocationLongitude - riderStartPointLongitude;

        // Average length of a degree of latitude in kilometers
        double kmPerDegreeLatitude = 111.32;

        // Average length of a degree of longitude in kilometers (considering average latitude)
        double kmPerDegreeLongitude = 111.32 * Math.cos(Math.toRadians((riderStartPointLatitude + driverCurrentPointLocationLatitude) / 2));

        double distanceLat = deltaLat * kmPerDegreeLatitude;
        double distanceLon = deltaLon * kmPerDegreeLongitude;

        // Use the Pythagoras formula to calculate the distance
        return Math.sqrt(distanceLat * distanceLat + distanceLon * distanceLon);
    }
}