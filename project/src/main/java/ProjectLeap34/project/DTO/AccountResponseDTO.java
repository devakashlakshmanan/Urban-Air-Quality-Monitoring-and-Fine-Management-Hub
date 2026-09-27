package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

    private Long id;
    private String AccountCode;
    private String AccountName;
    private String AccountType;
    private String Description;
    private Double Balance;
    private Boolean Active;
}
