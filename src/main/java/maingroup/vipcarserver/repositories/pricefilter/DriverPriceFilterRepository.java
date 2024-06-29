package maingroup.vipcarserver.repositories.pricefilter;

import maingroup.vipcarserver.entities.DriverFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverPriceFilterRepository extends JpaRepository<DriverFilter, Long> {
    Optional<DriverFilter> findFirstByDriverId(Long driverId);
}