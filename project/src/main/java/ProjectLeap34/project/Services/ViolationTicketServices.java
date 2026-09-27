package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.ViolationTicketRequestDTO;
import ProjectLeap34.project.DTO.ViolationTicketResponseDTO;
import ProjectLeap34.project.Models.ViolationTicket;
import ProjectLeap34.project.Repository.ViolationTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViolationTicketServices {

    @Autowired
    private ViolationTicketRepository violationTicketRepository;

    private ViolationTicket convertToEntity(ViolationTicketRequestDTO dto) {
        ViolationTicket ticket = new ViolationTicket();
        ticket.setTicketNumber(dto.getTicketNumber());
        ticket.setIndustrialPlantId(dto.getIndustrialPlantId());
        ticket.setSensorId(dto.getSensorId());
        ticket.setTelemetryId(dto.getTelemetryId());
        ticket.setEnvironmentalZone(dto.getEnvironmentalZone());
        ticket.setParameter(dto.getParameter());
        ticket.setMeasuredValue(dto.getMeasuredValue());
        ticket.setThresholdValue(dto.getThresholdValue());
        ticket.setViolationDate(dto.getViolationDate());
        ticket.setPenaltyAmount(dto.getPenaltyAmount());
        ticket.setStatus(dto.getStatus());
        return ticket;
    }

    private ViolationTicketResponseDTO convertToResponseDTO(ViolationTicket ticket) {
        ViolationTicketResponseDTO dto = new ViolationTicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        dto.setIndustrialPlantId(ticket.getIndustrialPlantId());
        dto.setSensorId(ticket.getSensorId());
        dto.setTelemetryId(ticket.getTelemetryId());
        dto.setEnvironmentalZone(ticket.getEnvironmentalZone());
        dto.setParameter(ticket.getParameter());
        dto.setMeasuredValue(ticket.getMeasuredValue());
        dto.setThresholdValue(ticket.getThresholdValue());
        dto.setViolationDate(ticket.getViolationDate());
        dto.setPenaltyAmount(ticket.getPenaltyAmount());
        dto.setStatus(ticket.getStatus());
        return dto;
    }

    public ViolationTicketResponseDTO createviolation(ViolationTicketRequestDTO data) {
        ViolationTicket ticket = convertToEntity(data);
        ViolationTicket saved = violationTicketRepository.save(ticket);
        return convertToResponseDTO(saved);
    }

    public List<ViolationTicketResponseDTO> getallviolation() {
        return violationTicketRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ViolationTicketResponseDTO updateviolation(ViolationTicketRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Violation Ticket ID is required for update");
        }
        ViolationTicket ticket = violationTicketRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Violation Ticket Not Found"));
        if (data.getTicketNumber() != null) ticket.setTicketNumber(data.getTicketNumber());
        if (data.getIndustrialPlantId() != null) ticket.setIndustrialPlantId(data.getIndustrialPlantId());
        if (data.getSensorId() != null) ticket.setSensorId(data.getSensorId());
        if (data.getTelemetryId() != null) ticket.setTelemetryId(data.getTelemetryId());
        if (data.getEnvironmentalZone() != null) ticket.setEnvironmentalZone(data.getEnvironmentalZone());
        if (data.getParameter() != null) ticket.setParameter(data.getParameter());
        if (data.getMeasuredValue() != null) ticket.setMeasuredValue(data.getMeasuredValue());
        if (data.getThresholdValue() != null) ticket.setThresholdValue(data.getThresholdValue());
        if (data.getViolationDate() != null) ticket.setViolationDate(data.getViolationDate());
        if (data.getPenaltyAmount() != null) ticket.setPenaltyAmount(data.getPenaltyAmount());
        if (data.getStatus() != null) ticket.setStatus(data.getStatus());
        ViolationTicket saved = violationTicketRepository.save(ticket);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        violationTicketRepository.deleteById(Id);
    }

    public ViolationTicketResponseDTO getbyid(Long Id) {
        ViolationTicket ticket = violationTicketRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Violation Ticket Not Found"));
        return convertToResponseDTO(ticket);
    }
}
