package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private String PaymentReference;
    private Long CustomerInvoiceId;
    private String PaymentDate;
    private Double Amount;
    private String PaymentMode;
    private String Status;
}
