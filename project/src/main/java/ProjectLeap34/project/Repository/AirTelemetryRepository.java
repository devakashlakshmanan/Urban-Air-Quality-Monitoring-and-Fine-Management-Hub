package ProjectLeap34.project.Repository;

import ProjectLeap34.project.Models.AirTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirTelemetryRepository extends JpaRepository<AirTelemetry, Long> {
}
