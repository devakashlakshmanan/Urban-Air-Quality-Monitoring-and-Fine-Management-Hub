package ProjectLeap34.project.Controller;

import ProjectLeap34.project.Models.*;
import ProjectLeap34.project.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/report/")
public class ReportController {

    @Autowired
    private AirSensorRepository airSensorRepository;

    @Autowired
    private AirTelemetryRepository airTelemetryRepository;

    @Autowired
    private ViolationTicketRepository violationTicketRepository;

    @Autowired
    private EnvironmentalBudgetRepository environmentalBudgetRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("zone")
    public ResponseEntity<?> getAirQualityZoneReport() {
        List<AirSensor> sensors = airSensorRepository.findAll();
        List<AirTelemetry> telemetries = airTelemetryRepository.findAll();
        List<ViolationTicket> violations = violationTicketRepository.findAll();

        Map<String, List<AirTelemetry>> telemetriesByZone = new HashMap<>();
        for (AirTelemetry t : telemetries) {
            String zone = t.getEnvironmentalZone() != null ? t.getEnvironmentalZone() : "Default Zone";
            telemetriesByZone.computeIfAbsent(zone, k -> new ArrayList<>()).add(t);
        }

        List<Map<String, Object>> zoneReports = new ArrayList<>();

        if (telemetriesByZone.isEmpty()) {
            Map<String, Object> emptyReport = new HashMap<>();
            emptyReport.put("zone", "Industrial Sector Air Zone 5");
            emptyReport.put("sensorCount", sensors.size());
            emptyReport.put("telemetryCount", 0);
            emptyReport.put("violationCount", violations.size());
            emptyReport.put("averagePm25", 0.0);
            emptyReport.put("maxPm25", 0.0);
            zoneReports.add(emptyReport);
        } else {
            for (Map.Entry<String, List<AirTelemetry>> entry : telemetriesByZone.entrySet()) {
                String zone = entry.getKey();
                List<AirTelemetry> zoneList = entry.getValue();

                long sensorCount = sensors.stream()
                        .filter(s -> zone.equalsIgnoreCase(s.getEnvironmentalZone()))
                        .count();
                long violationCount = violations.stream()
                        .filter(v -> zone.equalsIgnoreCase(v.getEnvironmentalZone()))
                        .count();

                double sumPm25 = 0.0;
                double maxPm25 = 0.0;
                for (AirTelemetry t : zoneList) {
                    if (t.getPm25() != null) {
                        sumPm25 += t.getPm25();
                        if (t.getPm25() > maxPm25) {
                            maxPm25 = t.getPm25();
                        }
                    }
                }
                double avgPm25 = zoneList.isEmpty() ? 0.0 : sumPm25 / zoneList.size();

                Map<String, Object> report = new HashMap<>();
                report.put("zone", zone);
                report.put("sensorCount", sensorCount);
                report.put("telemetryCount", zoneList.size());
                report.put("violationCount", violationCount);
                report.put("averagePm25", avgPm25);
                report.put("maxPm25", maxPm25);
                zoneReports.add(report);
            }
        }

        return new ResponseEntity<>(zoneReports, HttpStatus.OK);
    }

    @GetMapping("budget")
    public ResponseEntity<?> getBudgetReport() {
        List<EnvironmentalBudget> budgets = environmentalBudgetRepository.findAll();
        List<Map<String, Object>> budgetReports = new ArrayList<>();

        for (EnvironmentalBudget b : budgets) {
            double budgetAmount = b.getBudgetAmount() != null ? b.getBudgetAmount() : 0.0;
            double actualExpenditure = b.getActualExpenditure() != null ? b.getActualExpenditure() : 0.0;
            double fineCollections = b.getFineCollections() != null ? b.getFineCollections() : 0.0;
            double variance = budgetAmount - actualExpenditure;

            Map<String, Object> report = new HashMap<>();
            report.put("id", b.getId());
            report.put("budgetName", b.getBudgetName());
            report.put("zone", b.getEnvironmentalZone());
            report.put("budgetAmount", budgetAmount);
            report.put("actualExpenditure", actualExpenditure);
            report.put("fineCollections", fineCollections);
            report.put("variance", variance);
            report.put("fiscalYear", b.getFiscalYear());
            budgetReports.add(report);
        }

        return new ResponseEntity<>(budgetReports, HttpStatus.OK);
    }

    @GetMapping("financial")
    public ResponseEntity<?> getFinancialSummary() {
        List<JournalEntry> entries = journalEntryRepository.findAll();

        double fineRevenue = 0.0;
        double licensingRevenue = 0.0;
        double calibrationExpenses = 0.0;
        double gridExpenses = 0.0;

        for (JournalEntry je : entries) {
            if ("4100".equals(je.getAccountCode())) {
                fineRevenue += je.getCredit() != null ? je.getCredit() : 0.0;
            } else if ("4200".equals(je.getAccountCode())) {
                licensingRevenue += je.getCredit() != null ? je.getCredit() : 0.0;
            } else if ("5100".equals(je.getAccountCode())) {
                calibrationExpenses += je.getDebit() != null ? je.getDebit() : 0.0;
            } else if ("5200".equals(je.getAccountCode())) {
                gridExpenses += je.getDebit() != null ? je.getDebit() : 0.0;
            }
        }

        Map<String, Object> financialSummary = new HashMap<>();
        financialSummary.put("environmentalFineRevenue", fineRevenue);
        financialSummary.put("licensingRevenue", licensingRevenue);
        financialSummary.put("calibrationExpenses", calibrationExpenses);
        financialSummary.put("gridCommunicationExpenses", gridExpenses);

        return new ResponseEntity<>(financialSummary, HttpStatus.OK);
    }

    @GetMapping("balancesheet")
    public ResponseEntity<?> getBalanceSheet() {
        List<Account> accounts = accountRepository.findAll();

        double totalAssets = 0.0;
        double totalLiabilities = 0.0;
        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        for (Account acc : accounts) {
            double balance = acc.getBalance() != null ? acc.getBalance() : 0.0;
            String type = acc.getAccountType() != null ? acc.getAccountType().toUpperCase() : "";
            switch (type) {
                case "ASSET": totalAssets += balance; break;
                case "LIABILITY": totalLiabilities += balance; break;
                case "INCOME": totalIncome += balance; break;
                case "EXPENSE": totalExpenses += balance; break;
            }
        }

        Map<String, Object> balanceSheet = new HashMap<>();
        balanceSheet.put("totalAssets", totalAssets);
        balanceSheet.put("totalLiabilities", totalLiabilities);
        balanceSheet.put("totalIncome", totalIncome);
        balanceSheet.put("totalExpenses", totalExpenses);
        balanceSheet.put("netPosition", totalAssets - totalLiabilities);

        return new ResponseEntity<>(balanceSheet, HttpStatus.OK);
    }
}

