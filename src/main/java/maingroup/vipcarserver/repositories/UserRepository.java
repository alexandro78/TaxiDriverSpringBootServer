package maingroup.vipcarserver.repositories;

import maingroup.vipcarserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Method to get only user ID by email
    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    Optional<Long> findUserIdByEmail(String email);
}
