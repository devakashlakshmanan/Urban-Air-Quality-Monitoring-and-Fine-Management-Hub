package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorVendorRequestDTO {

    private Long Id;

    @NotBlank(message = "Vendor name is required")
    private String Name;

    @NotBlank(message = "Vendor code is required")
    private String VendorCode;

    private String ContactPerson;

    private String Phone;

    @Email(message = "Enter a valid email")
    private String Email;

    private String Address;

    private Boolean Active;
}
