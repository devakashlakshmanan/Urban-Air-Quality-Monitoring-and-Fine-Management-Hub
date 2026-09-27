package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CustomerInvoice {
    @Id
    @GeneratedValue
    Long Id;
    String InvoiceNumber;
    Long IndustrialPlantId;
    Long ViolationTicketId;
    Long ProductId;
    String InvoiceDate;
    Double Amount;
    String Status;
    String DueDate;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getInvoiceNumber() { return InvoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { InvoiceNumber = invoiceNumber; }
    public Long getIndustrialPlantId() { return IndustrialPlantId; }
    public void setIndustrialPlantId(Long industrialPlantId) { IndustrialPlantId = industrialPlantId; }
    public Long getViolationTicketId() { return ViolationTicketId; }
    public void setViolationTicketId(Long violationTicketId) { ViolationTicketId = violationTicketId; }
    public Long getProductId() { return ProductId; }
    public void setProductId(Long productId) { ProductId = productId; }
    public String getInvoiceDate() { return InvoiceDate; }
    public void setInvoiceDate(String invoiceDate) { InvoiceDate = invoiceDate; }
    public Double getAmount() { return Amount; }
    public void setAmount(Double amount) { Amount = amount; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
    public String getDueDate() { return DueDate; }
    public void setDueDate(String dueDate) { DueDate = dueDate; }
}
