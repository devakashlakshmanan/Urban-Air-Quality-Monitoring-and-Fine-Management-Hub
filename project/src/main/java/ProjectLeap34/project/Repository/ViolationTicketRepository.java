package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.ViolationTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationTicketRepository extends JpaRepository<ViolationTicket, Long> {
}
