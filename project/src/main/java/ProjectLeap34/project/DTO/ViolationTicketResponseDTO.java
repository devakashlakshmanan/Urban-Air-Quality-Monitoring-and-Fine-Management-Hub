package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViolationTicketResponseDTO {

    private Long id;
    private String TicketNumber;
    private Long IndustrialPlantId;
    private Long SensorId;
    private Long TelemetryId;
    private String EnvironmentalZone;
    private String Parameter;
    private Double MeasuredValue;
    private Double ThresholdValue;
    private String ViolationDate;
    private Double PenaltyAmount;
    private String Status;
}
