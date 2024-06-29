package maingroup.vipcarserver.repositories;

import maingroup.vipcarserver.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByUserId(Long userId);
    List<Setting> findByOption1(Boolean option1);
    List<Setting> findByOption2(Boolean option2);
}
