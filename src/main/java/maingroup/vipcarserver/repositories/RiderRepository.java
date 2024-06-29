package maingroup.vipcarserver.repositories;

import maingroup.vipcarserver.dtos.founddriverdto.SettingOptionsDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {

    @Query("SELECT new maingroup.taxicarserver.dtos.searchtripdtos.SearchRiderDataDto( " +
            "u.id as userId, " +
            "r.id as riderId, " +
            "sr.param1, " +
            "rc.comfort, " +
            "rc.lux, " +
            "rc.param3, " +
            "rc.param2, " +
            "ds.option1, " +
            "ds.option2, " +
            "sr.startPointLocationLatitude, " +
            "sr.startPointLocationLongitude, " +
            "COALESCE(rf.price, NULL)) " +
            "FROM User u " +
            "JOIN u.rider r " +
            "JOIN r.searchRides sr " +
            "JOIN u.setting ds " +
            "JOIN r.riderCarClass rc " +
            "LEFT JOIN r.riderFilters rf " +
            "WHERE u.id = :userId")
    SearchRiderDataDto findUserDataByUserId(@Param("userId") Long userId);

    @Query("SELECT r.savedRoute.id FROM Rider r WHERE r.id = :riderId")
    Long findSavedRouteByRiderId(@Param("riderId") Long riderId);

    @Query("SELECT new maingroup.vipcarserver.dtos.founddriverdto.OptionsSettingDto( " +
            "ds.option1, " +
            "ds.option2) " +
            "FROM Setting ds " +
            "JOIN ds.user u " +
            "WHERE u.id = :userId")
    SettingOptionsDto findSettingOptionsByUserId(@Param("userId") Long userId);
}
