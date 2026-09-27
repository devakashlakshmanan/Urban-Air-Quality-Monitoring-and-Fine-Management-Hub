package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.EnvironmentalBudgetRequestDTO;
import ProjectLeap34.project.DTO.EnvironmentalBudgetResponseDTO;
import ProjectLeap34.project.Models.EnvironmentalBudget;
import ProjectLeap34.project.Repository.EnvironmentalBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnvironmentalBudgetServices {

    @Autowired
    private EnvironmentalBudgetRepository environmentalBudgetRepository;

    private EnvironmentalBudget convertToEntity(EnvironmentalBudgetRequestDTO dto) {
        EnvironmentalBudget budget = new EnvironmentalBudget();
        budget.setBudgetName(dto.getBudgetName());
        budget.setEnvironmentalZone(dto.getEnvironmentalZone());
        budget.setBudgetAmount(dto.getBudgetAmount());
        budget.setActualExpenditure(dto.getActualExpenditure());
        budget.setFineCollections(dto.getFineCollections());
        budget.setFiscalYear(dto.getFiscalYear());
        return budget;
    }

    private EnvironmentalBudgetResponseDTO convertToResponseDTO(EnvironmentalBudget budget) {
        EnvironmentalBudgetResponseDTO dto = new EnvironmentalBudgetResponseDTO();
        dto.setId(budget.getId());
        dto.setBudgetName(budget.getBudgetName());
        dto.setEnvironmentalZone(budget.getEnvironmentalZone());
        dto.setBudgetAmount(budget.getBudgetAmount());
        dto.setActualExpenditure(budget.getActualExpenditure());
        dto.setFineCollections(budget.getFineCollections());
        dto.setFiscalYear(budget.getFiscalYear());
        return dto;
    }

    public EnvironmentalBudgetResponseDTO createbudget(EnvironmentalBudgetRequestDTO dto) {
        EnvironmentalBudget data = convertToEntity(dto);
        if (data.getActualExpenditure() == null) {
            data.setActualExpenditure(0.0);
        }
        if (data.getFineCollections() == null) {
            data.setFineCollections(0.0);
        }
        EnvironmentalBudget saved = environmentalBudgetRepository.save(data);
        return convertToResponseDTO(saved);
    }

    public List<EnvironmentalBudgetResponseDTO> getallbudget() {
        return environmentalBudgetRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public EnvironmentalBudgetResponseDTO updatebudget(EnvironmentalBudgetRequestDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Budget ID is required for update");
        }
        EnvironmentalBudget budget = environmentalBudgetRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Environmental Budget Not Found"));
        if (dto.getBudgetName() != null) budget.setBudgetName(dto.getBudgetName());
        if (dto.getEnvironmentalZone() != null) budget.setEnvironmentalZone(dto.getEnvironmentalZone());
        if (dto.getBudgetAmount() != null) budget.setBudgetAmount(dto.getBudgetAmount());
        if (dto.getActualExpenditure() != null) budget.setActualExpenditure(dto.getActualExpenditure());
        if (dto.getFineCollections() != null) budget.setFineCollections(dto.getFineCollections());
        if (dto.getFiscalYear() != null) budget.setFiscalYear(dto.getFiscalYear());
        EnvironmentalBudget saved = environmentalBudgetRepository.save(budget);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        environmentalBudgetRepository.deleteById(Id);
    }

    public EnvironmentalBudgetResponseDTO getbyid(Long Id) {
        EnvironmentalBudget budget = environmentalBudgetRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Environmental Budget Not Found"));
        return convertToResponseDTO(budget);
    }

    public Map<String, Object> getBudgetReport(Long Id) {
        EnvironmentalBudget budget = environmentalBudgetRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Environmental Budget Not Found"));
        double budgetAmount = budget.getBudgetAmount() != null ? budget.getBudgetAmount() : 0.0;
        double actualExpenditure = budget.getActualExpenditure() != null ? budget.getActualExpenditure() : 0.0;
        double fineCollections = budget.getFineCollections() != null ? budget.getFineCollections() : 0.0;
        double variance = budgetAmount - actualExpenditure;

        Map<String, Object> report = new HashMap<>();
        report.put("id", budget.getId());
        report.put("budgetName", budget.getBudgetName());
        report.put("environmentalZone", budget.getEnvironmentalZone());
        report.put("budgetAmount", budgetAmount);
        report.put("actualExpenditure", actualExpenditure);
        report.put("fineCollections", fineCollections);
        report.put("variance", variance);
        report.put("fiscalYear", budget.getFiscalYear());
        return report;
    }
}
