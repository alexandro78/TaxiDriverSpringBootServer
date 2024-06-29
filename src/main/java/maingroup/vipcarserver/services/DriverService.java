package maingroup.vipcarserver.services;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.entities.Driver;
import maingroup.vipcarserver.entities.Car;
import maingroup.vipcarserver.entities.User;
import maingroup.vipcarserver.repositories.DriverRepository;
import maingroup.vipcarserver.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public Driver createDriver(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check that the user does not already have a Driver record
        if (driverRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("Driver already exists for this user");
        }

        Driver driver = new Driver();
        driver.setUser(user);
        // Initialise other fields if necessary

        return driverRepository.save(driver);
    }

    ////////////////***** Методи пошуку driver в базі за параметрами rider ****///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //searchTypeCode = 1 //
    public List<SearchFitDriversDataDto> findDriversWithoutParam1(double latitude, double longitude) {
        Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
        return driverRepository.findDriversWithoutParam1(latitude, longitude, maxCarArrivalRadius);
    }

    //searchTypeCode = 2 //
    public List<SearchFitDriversDataDto> findDriversWithParam1(double latitude, double longitude) {
        Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
        return driverRepository.findDriversWithParam1(latitude, longitude, maxCarArrivalRadius);
    }

    //searchTypeCode = 3 //
    public List<SearchFitDriversDataDto> findDriversWithOption2WithoutParam1(double latitude, double longitude) {
        Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
        return driverRepository.findDriversWithOption1WithoutParam1(latitude, longitude, maxCarArrivalRadius);
    }

    //searchTypeCode = 4 //
    public List<SearchFitDriversDataDto> findDriversWithOption1WithParam1(double latitude, double longitude) {
        Double maxCarArrivalRadius = driverRepository.findMaxCarArrivalRadius();
        return driverRepository.findDriversWithOption1WithParam1(latitude, longitude, maxCarArrivalRadius);
    }

    //searchTypeCode = 5 //
    public List<SearchFitDriversDataDto> findDriversWithOption1WideRadiusWithoutParam1(double latitude, double longitude) {
        return driverRepository.findDriversWithOption1WideRadiusWithoutParam1(latitude, longitude);
    }

    //searchTypeCode = 6 //
    public List<SearchFitDriversDataDto> findDriversWithOption1WideRadius(double latitude, double longitude) {
        return driverRepository.findDriversWithOption1WRadius(latitude, longitude);
    }

    //searchTypeCode = 7 //
    public List<SearchFitDriversDataDto> findDriversWithOption2WideRadiusWithoutParam1(double latitude, double longitude) {
        return driverRepository.findDriversWithOption2WideRadiusWithoutParam1(latitude, longitude);
    }

    //searchTypeCode = 8 //
    public List<SearchFitDriversDataDto> findDriversWithOption2WideRadius(double latitude, double longitude) {
        return driverRepository.findDriversWithOption2WideRadius(latitude, longitude);
    }

    ////////////////////////////////////////////////////////////////////////////////
    // A universal method of finding drivers //
    public List<SearchFitDriversDataDto> findDrivers(
            BiFunction<Double, Double, List<SearchFitDriversDataDto>> searchFunction,
            double latitude, double longitude) {
        return searchFunction.apply(latitude, longitude);
    }
    //////////////////////////////////***** Методи пошуку driver в базі за параметрами rider ended ****////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean getParam1StatusForActiveCar(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        return driver.getCars().stream()
                .filter(Car::getActive)
                .findFirst()
                .map(Car::getParam1)
                .orElseThrow(() -> new IllegalArgumentException("Active car not found"));
    }
}
