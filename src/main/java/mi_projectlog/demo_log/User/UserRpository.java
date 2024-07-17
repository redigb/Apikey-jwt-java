package mi_projectlog.demo_log.User;

import mi_projectlog.demo_log.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRpository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
