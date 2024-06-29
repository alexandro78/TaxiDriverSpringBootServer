package maingroup.vipcarserver.repositories.calculatetriproutedistance;

import maingroup.vipcarserver.entities.CitySector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitySectorRepository extends JpaRepository<CitySector, Long> {
    Optional<CitySector> findFirstByOrderById();

    Optional<CitySector> findBySityName(String sityName);
}
