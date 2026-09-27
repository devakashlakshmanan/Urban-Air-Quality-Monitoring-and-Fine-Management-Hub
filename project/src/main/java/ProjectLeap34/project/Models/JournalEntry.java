package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class JournalEntry {
    @Id
    @GeneratedValue
    Long Id;
    String ReferenceNumber;
    String EntryDate;
    String AccountCode;
    String Description;
    Double Debit;
    Double Credit;
    String ReferenceType;
    Long ReferenceId;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getReferenceNumber() { return ReferenceNumber; }
    public void setReferenceNumber(String referenceNumber) { ReferenceNumber = referenceNumber; }
    public String getEntryDate() { return EntryDate; }
    public void setEntryDate(String entryDate) { EntryDate = entryDate; }
    public String getAccountCode() { return AccountCode; }
    public void setAccountCode(String accountCode) { AccountCode = accountCode; }
    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }
    public Double getDebit() { return Debit; }
    public void setDebit(Double debit) { Debit = debit; }
    public Double getCredit() { return Credit; }
    public void setCredit(Double credit) { Credit = credit; }
    public String getReferenceType() { return ReferenceType; }
    public void setReferenceType(String referenceType) { ReferenceType = referenceType; }
    public Long getReferenceId() { return ReferenceId; }
    public void setReferenceId(Long referenceId) { ReferenceId = referenceId; }
}
