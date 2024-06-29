package maingroup.vipcarserver.repositories;

import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUserId(Long userId);
    List<Driver> findByActiveStatus(Boolean status);

    @Query("SELECT MAX(d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN Option1Setting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' AND c.param1 = false " +
            "AND ds.option1 = false AND ds.option2 = false")
    Double findMaxCarArrivalRadius();

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, COALESCE(df.price, NULL), cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "LEFT JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN Option1Setting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' AND c.param1 = false " +
            "AND ds.option1 = false AND ds.option2 = false " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= :maxCarArrivalRadius")
    List<SearchFitDriversDataDto> findDriversWithoutParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("maxCarArrivalRadius") double maxCarArrivalRadius);

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' " +
            "AND ds.option1 = false AND ds.option2 = false " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= :maxCarArrivalRadius")
    List<SearchFitDriversDataDto> findDriversWithParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("maxCarArrivalRadius") double maxCarArrivalRadius);


    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' AND c.param1 = false " +
            "AND ds.option2 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= :maxCarArrivalRadius")
    List<SearchFitDriversDataDto> findDriversWithOption1WithoutParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("maxCarArrivalRadius") double maxCarArrivalRadius);

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' " +
            "AND ds.option2 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= :maxCarArrivalRadius")
    List<SearchFitDriversDataDto> findDriversWithOption1WithParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("maxCarArrivalRadius") double maxCarArrivalRadius);

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' AND c.param1 = false " +
            "AND ds.option1 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= 15")
    List<SearchFitDriversDataDto> findDriversWithOption1WideRadiusWithoutParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude);

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' " +
            "AND ds.option1 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= 15")
    List<SearchFitDriversDataDto> findDriversWithOption1WRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude);


    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' AND c.param1 = false " +
            "AND ds.option2 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= 15")
    List<SearchFitDriversDataDto> findDriversWithOption2WideRadiusWithoutParam1(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude);

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchFitDriversDataDto(u.id, d.id, df.price, cc.comfort, cc.lux, cc.premium, cc.elite, d.currentLocationLatitude, d.currentLocationLongitude, d.carArrivalRadius) " +
            "FROM Driver d " +
            "JOIN d.cars c " +
            "JOIN d.user u " +
            "JOIN d.driverFilters df " +
            "JOIN c.carClass cc " +
            "JOIN OptionsSetting ds ON ds.user = u " +
            "WHERE d.activeStatus = true AND d.driveStatus = 'FREE' " +
            "AND ds.option2 = true " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.currentLocationLatitude)) * cos(radians(d.currentLocationLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(d.currentLocationLatitude)))) <= 15")
    List<SearchFitDriversDataDto> findDriversWithOption2WideRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude);
}