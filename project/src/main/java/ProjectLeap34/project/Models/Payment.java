package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue
    Long Id;
    String PaymentReference;
    Long CustomerInvoiceId;
    String PaymentDate;
    Double Amount;
    String PaymentMode;
    String Status;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getPaymentReference() { return PaymentReference; }
    public void setPaymentReference(String paymentReference) { PaymentReference = paymentReference; }
    public Long getCustomerInvoiceId() { return CustomerInvoiceId; }
    public void setCustomerInvoiceId(Long customerInvoiceId) { CustomerInvoiceId = customerInvoiceId; }
    public String getPaymentDate() { return PaymentDate; }
    public void setPaymentDate(String paymentDate) { PaymentDate = paymentDate; }
    public Double getAmount() { return Amount; }
    public void setAmount(Double amount) { Amount = amount; }
    public String getPaymentMode() { return PaymentMode; }
    public void setPaymentMode(String paymentMode) { PaymentMode = paymentMode; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}
