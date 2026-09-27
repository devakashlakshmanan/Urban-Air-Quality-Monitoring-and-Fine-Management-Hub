package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {
}
