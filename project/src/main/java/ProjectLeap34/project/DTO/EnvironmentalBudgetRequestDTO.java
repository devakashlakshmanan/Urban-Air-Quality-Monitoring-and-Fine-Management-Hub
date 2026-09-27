package ProjectLeap34.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalBudgetRequestDTO {

    private Long Id;

    @NotBlank(message = "Budget name is required")
    private String BudgetName;

    private String EnvironmentalZone;

    private Double BudgetAmount;

    private Double ActualExpenditure;

    private Double FineCollections;

    private String FiscalYear;
}
