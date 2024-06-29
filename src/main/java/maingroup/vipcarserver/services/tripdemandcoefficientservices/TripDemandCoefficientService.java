package maingroup.vipcarserver.services.tripdemandcoefficientservices;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.coefficientcountdto.CoefficientCountDto;
import maingroup.vipcarserver.dtos.searchtripdtos.StartCurrentRiderLocationDto;
import maingroup.vipcarserver.repositories.calculatetripdemandcoefficient.TripDemandCoefficientRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TripDemandCoefficientService {
    private final TripDemandCoefficientRepository tripDemandCoefficientRepository;
    private static final double FACTOR_INCREASE = 0.1;

    public StartCurrentRiderLocationDto getCurrentRiderLocation(Long riderId) {
        return tripDemandCoefficientRepository.findStartPointLocationByRiderId(riderId)
                .orElseThrow(() -> new NoSuchElementException("Rider location not found for id: " + riderId));
    }

    public Integer countNearbyAvailableDrivers(Double startPointLocationLatitude, Double startPointLocationLongitude) {
        Long count = tripDemandCoefficientRepository.countNearbyAvailableDrivers(startPointLocationLatitude, startPointLocationLongitude);
        return count == 0 ? 1 : count.intValue();
    }

    public Double calculateDemandCoefficient(Long tripSearchCount, Integer availableDriversCount) {
        if (tripSearchCount <= availableDriversCount) {
            return 1.0; //In case rides is less than active drivers
        } else {
            return 1 + ((double)(tripSearchCount - availableDriversCount) / availableDriversCount) * FACTOR_INCREASE;
        }
    }

    public CoefficientCountDto getCurrentTripDemandCoefficient(Long riderId) {
        StartCurrentRiderLocationDto riderLocation = getCurrentRiderLocation(riderId);
        Double startPointLocationLatitude = riderLocation.getStartPointLocationLatitude();
        Double startPointLocationLongitude = riderLocation.getStartPointLocationLongitude();

        Long tripSearchCount = tripDemandCoefficientRepository.countNearbySearchRides(startPointLocationLatitude, startPointLocationLongitude);
        Integer availableDriversCount = countNearbyAvailableDrivers(startPointLocationLatitude, startPointLocationLongitude);
        
        Double coefficient = calculateDemandCoefficient(tripSearchCount, availableDriversCount);
        return new CoefficientCountDto(tripSearchCount.intValue(), availableDriversCount, coefficient);
    }
}
