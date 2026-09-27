package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBillResponseDTO {

    private Long id;
    private String BillNumber;
    private Long PurchaseOrderId;
    private Long VendorId;
    private String BillDate;
    private Double Amount;
    private String Status;
}
