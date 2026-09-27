package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {

    private Long Id;

    @NotBlank(message = "Account code is required")
    private String AccountCode;

    @NotBlank(message = "Account name is required")
    private String AccountName;

    private String AccountType;

    private String Description;

    private Double Balance;

    private Boolean Active;
}
