package maingroup.vipcarserver.repositories.pricefilter;

import maingroup.vipcarserver.entities.RiderFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderPriceFilterRepository extends JpaRepository<RiderFilter, Long> {
    Optional<RiderFilter> findFirstByRiderId(Long riderId);
}
