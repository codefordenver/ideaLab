package idealab.api.repositories;

import idealab.api.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}

