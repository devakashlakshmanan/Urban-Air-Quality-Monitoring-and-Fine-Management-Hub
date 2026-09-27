package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.AirTelemetryRequestDTO;
import ProjectLeap34.project.DTO.AirTelemetryResponseDTO;
import ProjectLeap34.project.Models.AirSensor;
import ProjectLeap34.project.Models.AirTelemetry;
import ProjectLeap34.project.Models.ComplianceProduct;
import ProjectLeap34.project.Models.ViolationTicket;
import ProjectLeap34.project.Repository.AirSensorRepository;
import ProjectLeap34.project.Repository.AirTelemetryRepository;
import ProjectLeap34.project.Repository.ComplianceProductRepository;
import ProjectLeap34.project.Repository.ViolationTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirTelemetryServices {

    @Autowired
    private AirTelemetryRepository airTelemetryRepository;

    @Autowired
    private AirSensorRepository airSensorRepository;

    @Autowired
    private ViolationTicketRepository violationTicketRepository;

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private ComplianceProductRepository complianceProductRepository;

    @Autowired
    private ProjectLeap34.project.Repository.CustomerInvoiceRepository customerInvoiceRepository;

    private AirTelemetry convertToEntity(AirTelemetryRequestDTO dto) {
        AirTelemetry telemetry = new AirTelemetry();
        telemetry.setSensorId(dto.getSensorId());
        telemetry.setPm25(dto.getPm25());
        telemetry.setCo2(dto.getCo2());
        telemetry.setRecordedAt(dto.getRecordedAt());
        telemetry.setEnvironmentalZone(dto.getEnvironmentalZone());
        return telemetry;
    }

    private AirTelemetryResponseDTO convertToResponseDTO(AirTelemetry telemetry) {
        AirTelemetryResponseDTO dto = new AirTelemetryResponseDTO();
        dto.setId(telemetry.getId());
        dto.setSensorId(telemetry.getSensorId());
        dto.setPm25(telemetry.getPm25());
        dto.setCo2(telemetry.getCo2());
        dto.setRecordedAt(telemetry.getRecordedAt());
        dto.setEnvironmentalZone(telemetry.getEnvironmentalZone());
        return dto;
    }

    public AirTelemetryResponseDTO createtelemetry(AirTelemetryRequestDTO dto) {
        AirTelemetry data = convertToEntity(dto);
        if (data.getRecordedAt() == null || data.getRecordedAt().isEmpty()) {
            data.setRecordedAt(LocalDateTime.now().toString());
        }

        AirTelemetry savedTelemetry = airTelemetryRepository.save(data);

        double threshold = 100.0;
        String zone = data.getEnvironmentalZone();
        Long plantId = null;

        if (data.getSensorId() != null) {
            Optional<AirSensor> sensorOpt = airSensorRepository.findById(data.getSensorId());
            if (sensorOpt.isPresent()) {
                AirSensor sensor = sensorOpt.get();
                
                if (sensor.getPm25Threshold() != null && sensor.getPm25Threshold() > 0) {
                    threshold = sensor.getPm25Threshold();
                }
                if (zone == null || zone.isEmpty()) {
                    zone = sensor.getEnvironmentalZone();
                }
                plantId = sensor.getIndustrialPlantId();
            }
        }

        // Task 3a: fall back to 1L only if sensor has no plant linked
        if (plantId == null) {
            plantId = 1L;
        }

        if (data.getPm25() != null && data.getPm25() > threshold) {
            // Task 3b: look up penalty amount from Product Master instead of hardcoded value
            double penaltyAmount = 5000.0;
            Optional<ComplianceProduct> penaltyProduct = complianceProductRepository.findAll().stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains("penalty"))
                    .findFirst();
            if (penaltyProduct.isPresent() && penaltyProduct.get().getUnitPrice() != null) {
                penaltyAmount = penaltyProduct.get().getUnitPrice();
            }

            ViolationTicket ticket = new ViolationTicket();
            ticket.setTicketNumber("VIOL-" + System.currentTimeMillis());
            ticket.setIndustrialPlantId(plantId);
            ticket.setSensorId(data.getSensorId());
            ticket.setTelemetryId(savedTelemetry.getId());
            ticket.setEnvironmentalZone(zone != null ? zone : "Industrial Sector Air Zone 5");
            ticket.setParameter("PM2.5");
            ticket.setMeasuredValue(data.getPm25());
            ticket.setThresholdValue(threshold);
            ticket.setViolationDate(savedTelemetry.getRecordedAt());
            ticket.setPenaltyAmount(penaltyAmount);
            ticket.setStatus("OPEN");

            ViolationTicket savedTicket = violationTicketRepository.save(ticket);
            journalEntryServices.createFineJournalEntry(savedTicket.getId(), savedTicket.getPenaltyAmount());

            ProjectLeap34.project.Models.CustomerInvoice invoice = new ProjectLeap34.project.Models.CustomerInvoice();
            invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
            invoice.setIndustrialPlantId(plantId);
            invoice.setViolationTicketId(savedTicket.getId());
            if (penaltyProduct.isPresent()) {
                invoice.setProductId(penaltyProduct.get().getId());
            } else {
                invoice.setProductId(1L);
            }
            invoice.setInvoiceDate(java.time.LocalDate.now().toString());
            invoice.setDueDate(java.time.LocalDate.now().plusDays(30).toString());
            invoice.setAmount(savedTicket.getPenaltyAmount());
            invoice.setStatus("UNPAID");
            customerInvoiceRepository.save(invoice);
        }

        return convertToResponseDTO(savedTelemetry);
    }

    public List<AirTelemetryResponseDTO> getalltelemetry() {
        return airTelemetryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletebyid(Long Id) {
        airTelemetryRepository.deleteById(Id);
    }

    public AirTelemetryResponseDTO getbyid(Long Id) {
        AirTelemetry telemetry = airTelemetryRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Air Telemetry Not Found"));
        return convertToResponseDTO(telemetry);
    }
}
