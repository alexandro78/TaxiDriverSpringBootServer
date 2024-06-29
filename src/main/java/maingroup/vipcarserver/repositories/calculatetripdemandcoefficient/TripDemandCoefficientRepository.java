package maingroup.vipcarserver.repositories.calculatetripdemandcoefficient;

import maingroup.vipcarserver.dtos.searchtripdtos.StartCurrentRiderLocationDto;
import maingroup.vipcarserver.entities.SearchRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripDemandCoefficientRepository extends JpaRepository<SearchRide, Long> {

    @Query("SELECT new maingroup.vipcarserver.dtos.searchtripdtos.StartCurrentRiderLocationDto(s.startPointLocationLatitude, s.startPointLocationLongitude) " +
            "FROM SearchRide s " +
            "WHERE s.rider.id = :riderId")
    Optional<StartCurrentRiderLocationDto> findStartPointLocationByRiderId(@Param("riderId") Long riderId);


    @Query("SELECT COUNT(sr) " +
            "FROM SearchRide sr " +
            "WHERE (sr.driveStatus = 'NEW' OR sr.driveStatus = 'SEARCH') " +
            "AND (6371 * acos(cos(radians(:startPointLocationLatitude)) * cos(radians(sr.startPointLocationLatitude)) * " +
            "cos(radians(sr.startPointLocationLongitude) - radians(:startPointLocationLongitude)) + " +
            "sin(radians(:startPointLocationLatitude)) * sin(radians(sr.startPointLocationLatitude)))) <= 3.5")
    Long countNearbySearchRides(
            @Param("startPointLocationLatitude") Double startPointLocationLatitude,
            @Param("startPointLocationLongitude") Double startPointLocationLongitude);


    @Query("SELECT COUNT(d) " +
            "FROM Driver d " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' " +
            "AND (6371 * acos(cos(radians(:startPointLocationLatitude)) * cos(radians(d.currentLocationLatitude)) * " +
            "cos(radians(d.currentLocationLongitude) - radians(:startPointLocationLongitude)) + " +
            "sin(radians(:startPointLocationLatitude)) * sin(radians(d.currentLocationLatitude)))) <= 3.5")
    Long countNearbyAvailableDrivers(
            @Param("startPointLocationLatitude") Double startPointLocationLatitude,
            @Param("startPointLocationLongitude") Double startPointLocationLongitude);
}
