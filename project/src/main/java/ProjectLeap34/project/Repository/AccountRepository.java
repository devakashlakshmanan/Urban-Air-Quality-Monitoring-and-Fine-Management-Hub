package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
