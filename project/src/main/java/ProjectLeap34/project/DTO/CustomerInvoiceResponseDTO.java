package ProjectLeap34.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceResponseDTO {

    private Long id;
    private String InvoiceNumber;
    private Long IndustrialPlantId;
    private Long ViolationTicketId;
    private Long ProductId;
    private String InvoiceDate;
    private Double Amount;
    private String Status;
    private String DueDate;
}
