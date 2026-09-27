package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ComplianceProduct {
    @Id
    @GeneratedValue
    Long Id;
    String Name;
    String Code;
    String ProductType;
    String Description;
    Double UnitPrice;
    Boolean Active;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
    public String getCode() { return Code; }
    public void setCode(String code) { Code = code; }
    public String getProductType() { return ProductType; }
    public void setProductType(String productType) { ProductType = productType; }
    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }
    public Double getUnitPrice() { return UnitPrice; }
    public void setUnitPrice(Double unitPrice) { UnitPrice = unitPrice; }
    public Boolean getActive() { return Active; }
    public void setActive(Boolean active) { Active = active; }
}
