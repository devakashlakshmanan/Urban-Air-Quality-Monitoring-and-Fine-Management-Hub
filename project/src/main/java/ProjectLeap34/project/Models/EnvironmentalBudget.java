package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EnvironmentalBudget {
    @Id
    @GeneratedValue
    Long Id;
    String BudgetName;
    String EnvironmentalZone;
    Double BudgetAmount;
    Double ActualExpenditure;
    Double FineCollections;
    String FiscalYear;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getBudgetName() { return BudgetName; }
    public void setBudgetName(String budgetName) { BudgetName = budgetName; }
    public String getEnvironmentalZone() { return EnvironmentalZone; }
    public void setEnvironmentalZone(String environmentalZone) { EnvironmentalZone = environmentalZone; }
    public Double getBudgetAmount() { return BudgetAmount; }
    public void setBudgetAmount(Double budgetAmount) { BudgetAmount = budgetAmount; }
    public Double getActualExpenditure() { return ActualExpenditure; }
    public void setActualExpenditure(Double actualExpenditure) { ActualExpenditure = actualExpenditure; }
    public Double getFineCollections() { return FineCollections; }
    public void setFineCollections(Double fineCollections) { FineCollections = fineCollections; }
    public String getFiscalYear() { return FiscalYear; }
    public void setFiscalYear(String fiscalYear) { FiscalYear = fiscalYear; }
}
