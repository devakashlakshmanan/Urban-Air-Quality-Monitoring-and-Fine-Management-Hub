package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirSensorRequestDTO {

    private Long Id;

    @NotBlank(message = "Sensor code is required")
    private String SensorCode;

    @NotBlank(message = "Sensor name is required")
    private String SensorName;

    private Long IndustrialPlantId;
    
    private String Location;

    private String EnvironmentalZone;

    private String Manufacturer;

    private Double Pm25Threshold;

    private Double Co2Threshold;

    private String Status;

    private String InstallationDate;

    private Boolean Active;
}
