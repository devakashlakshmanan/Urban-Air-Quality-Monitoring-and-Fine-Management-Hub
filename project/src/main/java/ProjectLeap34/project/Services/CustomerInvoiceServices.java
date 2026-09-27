package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.CustomerInvoiceRequestDTO;
import ProjectLeap34.project.DTO.CustomerInvoiceResponseDTO;
import ProjectLeap34.project.Models.CustomerInvoice;
import ProjectLeap34.project.Models.ViolationTicket;
import ProjectLeap34.project.Repository.CustomerInvoiceRepository;
import ProjectLeap34.project.Repository.ViolationTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerInvoiceServices {

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Autowired
    private ViolationTicketRepository violationTicketRepository;

    private CustomerInvoice convertToEntity(CustomerInvoiceRequestDTO dto) {
        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setIndustrialPlantId(dto.getIndustrialPlantId());
        invoice.setViolationTicketId(dto.getViolationTicketId());
        invoice.setProductId(dto.getProductId());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setAmount(dto.getAmount());
        invoice.setStatus(dto.getStatus());
        invoice.setDueDate(dto.getDueDate());
        return invoice;
    }

    private CustomerInvoiceResponseDTO convertToResponseDTO(CustomerInvoice invoice) {
        CustomerInvoiceResponseDTO dto = new CustomerInvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setIndustrialPlantId(invoice.getIndustrialPlantId());
        dto.setViolationTicketId(invoice.getViolationTicketId());
        dto.setProductId(invoice.getProductId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setAmount(invoice.getAmount());
        dto.setStatus(invoice.getStatus());
        dto.setDueDate(invoice.getDueDate());
        return dto;
    }

    public CustomerInvoiceResponseDTO createinvoice(CustomerInvoiceRequestDTO dto) {
        CustomerInvoice data = convertToEntity(dto);
        if (data.getInvoiceNumber() == null || data.getInvoiceNumber().isEmpty()) {
            data.setInvoiceNumber("INV-" + System.currentTimeMillis());
        }
        if (data.getInvoiceDate() == null || data.getInvoiceDate().isEmpty()) {
            data.setInvoiceDate(LocalDate.now().toString());
        }
        if (data.getDueDate() == null || data.getDueDate().isEmpty()) {
            data.setDueDate(LocalDate.now().plusDays(30).toString());
        }
        if (data.getStatus() == null || data.getStatus().isEmpty()) {
            data.setStatus("UNPAID");
        }

        CustomerInvoice savedInvoice = customerInvoiceRepository.save(data);

        if (data.getViolationTicketId() != null) {
            Optional<ViolationTicket> ticketOpt = violationTicketRepository.findById(data.getViolationTicketId());
            if (ticketOpt.isPresent()) {
                ViolationTicket ticket = ticketOpt.get();
                ticket.setStatus("INVOICED");
                violationTicketRepository.save(ticket);
            }
        }

        return convertToResponseDTO(savedInvoice);
    }

    public List<CustomerInvoiceResponseDTO> getallinvoice() {
        return customerInvoiceRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public CustomerInvoiceResponseDTO updateinvoice(CustomerInvoiceRequestDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Invoice ID is required for update");
        }
        CustomerInvoice invoice = customerInvoiceRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Customer Invoice Not Found"));
        if (dto.getInvoiceNumber() != null) invoice.setInvoiceNumber(dto.getInvoiceNumber());
        if (dto.getIndustrialPlantId() != null) invoice.setIndustrialPlantId(dto.getIndustrialPlantId());
        if (dto.getViolationTicketId() != null) invoice.setViolationTicketId(dto.getViolationTicketId());
        if (dto.getProductId() != null) invoice.setProductId(dto.getProductId());
        if (dto.getInvoiceDate() != null) invoice.setInvoiceDate(dto.getInvoiceDate());
        if (dto.getAmount() != null) invoice.setAmount(dto.getAmount());
        if (dto.getStatus() != null) invoice.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) invoice.setDueDate(dto.getDueDate());
        CustomerInvoice saved = customerInvoiceRepository.save(invoice);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        customerInvoiceRepository.deleteById(Id);
    }

    public CustomerInvoiceResponseDTO getbyid(Long Id) {
        CustomerInvoice invoice = customerInvoiceRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Customer Invoice Not Found"));
        return convertToResponseDTO(invoice);
    }
}
