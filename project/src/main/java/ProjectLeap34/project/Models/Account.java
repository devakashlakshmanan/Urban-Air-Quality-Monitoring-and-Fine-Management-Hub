package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    Long Id;
    String AccountCode;
    String AccountName;
    String AccountType;
    String Description;
    Double Balance;
    Boolean Active;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getAccountCode() { return AccountCode; }
    public void setAccountCode(String accountCode) { AccountCode = accountCode; }
    public String getAccountName() { return AccountName; }
    public void setAccountName(String accountName) { AccountName = accountName; }
    public String getAccountType() { return AccountType; }
    public void setAccountType(String accountType) { AccountType = accountType; }
    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }
    public Double getBalance() { return Balance; }
    public void setBalance(Double balance) { Balance = balance; }
    public Boolean getActive() { return Active; }
    public void setActive(Boolean active) { Active = active; }
}
