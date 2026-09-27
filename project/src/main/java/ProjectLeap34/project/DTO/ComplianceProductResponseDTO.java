package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceProductResponseDTO {

    private Long id;
    private String Name;
    private String Code;
    private String ProductType;
    private String Description;
    private Double UnitPrice;
    private Boolean Active;
}
