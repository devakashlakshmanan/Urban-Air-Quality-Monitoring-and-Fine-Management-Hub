package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceProductRequestDTO {

    private Long Id;

    @NotBlank(message = "Product name is required")
    private String Name;

    @NotBlank(message = "Product code is required")
    private String Code;

    private String ProductType;

    private String Description;

    private Double UnitPrice;

    private Boolean Active;
}
