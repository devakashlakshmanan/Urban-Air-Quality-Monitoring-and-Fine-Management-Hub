package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalZoneRequestDTO {

    private Long Id;

    @NotBlank(message = "Zone code is required")
    private String ZoneCode;

    @NotBlank(message = "Zone name is required")
    private String ZoneName;

    private Double Pm25Limit;

    private Double Co2Limit;

    private String Description;

    private Boolean Active;
}
