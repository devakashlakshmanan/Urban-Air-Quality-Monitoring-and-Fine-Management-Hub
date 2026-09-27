package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirTelemetryRequestDTO {

    @NotNull(message = "Sensor ID is required")
    private Long SensorId;

    @NotNull(message = "PM2.5 value is required")
    private Double Pm25;

    private Double Co2;

    private String RecordedAt;

    private String EnvironmentalZone;
}
