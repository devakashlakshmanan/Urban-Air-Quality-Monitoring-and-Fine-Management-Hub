package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue
    Long Id;
    String PurchaseOrderNumber;
    Long VendorId;
    Long ProductId;
    String OrderDate;
    Integer Quantity;
    Double Amount;
    String Status;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getPurchaseOrderNumber() { return PurchaseOrderNumber; }
    public void setPurchaseOrderNumber(String purchaseOrderNumber) { PurchaseOrderNumber = purchaseOrderNumber; }
    public Long getVendorId() { return VendorId; }
    public void setVendorId(Long vendorId) { VendorId = vendorId; }
    public Long getProductId() { return ProductId; }
    public void setProductId(Long productId) { ProductId = productId; }
    public String getOrderDate() { return OrderDate; }
    public void setOrderDate(String orderDate) { OrderDate = orderDate; }
    public Integer getQuantity() { return Quantity; }
    public void setQuantity(Integer quantity) { Quantity = quantity; }
    public Double getAmount() { return Amount; }
    public void setAmount(Double amount) { Amount = amount; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}
