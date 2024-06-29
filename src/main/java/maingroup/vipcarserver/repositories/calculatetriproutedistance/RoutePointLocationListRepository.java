package maingroup.vipcarserver.repositories.calculatetriproutedistance;

import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.RoutePointLocationListDto;
import maingroup.vipcarserver.entities.SearchRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePointLocationListRepository extends JpaRepository<SearchRide, Long> {

    @Query("SELECT new maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.RoutePointLocationListDto(" +
            "rp.id, rp.savedRoute.rider.id, rp.savedRoute.id, rp.orderIndex, rp.pointLocationLatitude, rp.pointLocationLongitude, rp.timestamp) " +
            "FROM RoutePoint rp " +
            "JOIN rp.savedRoute savedRoute " +
            "JOIN savedRoute.searchRides searchRide " +
            "WHERE searchRide.rider.id = :riderId")
    List<RoutePointLocationListDto> getAllPointLocationRouteListByRiderId(@Param("riderId") Long riderId);
}
