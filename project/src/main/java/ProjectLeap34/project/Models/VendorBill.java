package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class VendorBill {
    @Id
    @GeneratedValue
    Long Id;
    String BillNumber;
    Long PurchaseOrderId;
    Long VendorId;
    String BillDate;
    Double Amount;
    String Status;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getBillNumber() { return BillNumber; }
    public void setBillNumber(String billNumber) { BillNumber = billNumber; }
    public Long getPurchaseOrderId() { return PurchaseOrderId; }
    public void setPurchaseOrderId(Long purchaseOrderId) { PurchaseOrderId = purchaseOrderId; }
    public Long getVendorId() { return VendorId; }
    public void setVendorId(Long vendorId) { VendorId = vendorId; }
    public String getBillDate() { return BillDate; }
    public void setBillDate(String billDate) { BillDate = billDate; }
    public Double getAmount() { return Amount; }
    public void setAmount(Double amount) { Amount = amount; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}
