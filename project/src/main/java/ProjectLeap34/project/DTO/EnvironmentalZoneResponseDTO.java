package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalZoneResponseDTO {

    private Long id;
    private String ZoneCode;
    private String ZoneName;
    private Double Pm25Limit;
    private Double Co2Limit;
    private String Description;
    private Boolean Active;
}
