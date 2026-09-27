package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustrialPlantRequestDTO {

    private Long Id;

    @NotBlank(message = "Plant name is required")
    private String Name;

    @NotBlank(message = "Plant code is required")
    private String PlantCode;

    private String IndustryType;

    private String Address;

    private String ContactPerson;

    private String Phone;

    @Email(message = "Enter a valid email")
    private String Email;

    private String EnvironmentalZone;

    private Boolean Active;
}
