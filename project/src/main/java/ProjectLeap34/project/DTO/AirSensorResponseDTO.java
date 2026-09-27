package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirSensorResponseDTO {

    private Long id;
    private Long IndustrialPlantId;
    private String SensorCode;
    private String SensorName;
    private String Location;
    private String EnvironmentalZone;
    private String Manufacturer;
    private Double Pm25Threshold;
    private Double Co2Threshold;
    private String Status;
    private String InstallationDate;
    private Boolean Active;
}
