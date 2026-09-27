package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalBudgetResponseDTO {

    private Long id;
    private String BudgetName;
    private String EnvironmentalZone;
    private Double BudgetAmount;
    private Double ActualExpenditure;
    private Double FineCollections;
    private String FiscalYear;
}
