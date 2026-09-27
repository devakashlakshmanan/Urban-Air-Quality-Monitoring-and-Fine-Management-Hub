package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirTelemetryResponseDTO {

    private Long id;
    private Long SensorId;
    private Double Pm25;
    private Double Co2;
    private String RecordedAt;
    private String EnvironmentalZone;
}
