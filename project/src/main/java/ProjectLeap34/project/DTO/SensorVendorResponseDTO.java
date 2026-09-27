package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorVendorResponseDTO {

    private Long id;
    private String Name;
    private String VendorCode;
    private String ContactPerson;
    private String Phone;
    private String Email;
    private String Address;
    private Boolean Active;
}
