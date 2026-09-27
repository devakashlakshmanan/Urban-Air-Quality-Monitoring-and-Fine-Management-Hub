package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustrialPlantResponseDTO {

    private Long id;
    private String Name;
    private String PlantCode;
    private String IndustryType;
    private String Address;
    private String ContactPerson;
    private String Phone;
    private String Email;
    private String EnvironmentalZone;
    private Boolean Active;
}
