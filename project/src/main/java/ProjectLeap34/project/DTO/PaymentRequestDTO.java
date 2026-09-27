package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    private String PaymentReference;

    private Long CustomerInvoiceId;

    private String PaymentDate;

    private Double Amount;

    private String PaymentMode;

    private String Status;
}
