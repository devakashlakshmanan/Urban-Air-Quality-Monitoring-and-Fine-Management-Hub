package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponseDTO {

    private Long id;
    private String PurchaseOrderNumber;
    private Long VendorId;
    private Long ProductId;
    private String OrderDate;
    private Integer Quantity;
    private Double Amount;
    private String Status;
}
