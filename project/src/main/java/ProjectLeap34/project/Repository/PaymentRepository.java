package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
