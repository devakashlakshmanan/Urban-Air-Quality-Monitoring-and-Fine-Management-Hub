package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.PaymentRequestDTO;
import ProjectLeap34.project.DTO.PaymentResponseDTO;
import ProjectLeap34.project.Models.CustomerInvoice;
import ProjectLeap34.project.Models.Payment;
import ProjectLeap34.project.Models.ViolationTicket;
import ProjectLeap34.project.Repository.CustomerInvoiceRepository;
import ProjectLeap34.project.Repository.PaymentRepository;
import ProjectLeap34.project.Repository.ViolationTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServices {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Autowired
    private ViolationTicketRepository violationTicketRepository;

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private ProjectLeap34.project.Repository.EnvironmentalBudgetRepository environmentalBudgetRepository;

    private Payment convertToEntity(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setPaymentReference(dto.getPaymentReference());
        payment.setCustomerInvoiceId(dto.getCustomerInvoiceId());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setStatus(dto.getStatus());
        return payment;
    }

    private PaymentResponseDTO convertToResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setPaymentReference(payment.getPaymentReference());
        dto.setCustomerInvoiceId(payment.getCustomerInvoiceId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setStatus(payment.getStatus());
        return dto;
    }

    public PaymentResponseDTO createpayment(PaymentRequestDTO dto) {
        Payment data = convertToEntity(dto);
        if (data.getPaymentReference() == null || data.getPaymentReference().isEmpty()) {
            data.setPaymentReference("DEMO-PAY-" + System.currentTimeMillis());
        }
        if (data.getPaymentDate() == null || data.getPaymentDate().isEmpty()) {
            data.setPaymentDate(LocalDate.now().toString());
        }
        if (data.getPaymentMode() == null || data.getPaymentMode().isEmpty()) {
            data.setPaymentMode("BANK_TRANSFER");
        }
        data.setStatus("SUCCESS");

        Payment savedPayment = paymentRepository.save(data);

        if (data.getCustomerInvoiceId() != null) {
            Optional<CustomerInvoice> invoiceOpt = customerInvoiceRepository.findById(data.getCustomerInvoiceId());
            if (invoiceOpt.isPresent()) {
                CustomerInvoice invoice = invoiceOpt.get();
                invoice.setStatus("PAID");
                customerInvoiceRepository.save(invoice);

                String zone = null;
                if (invoice.getViolationTicketId() != null) {
                    Optional<ViolationTicket> ticketOpt = violationTicketRepository.findById(invoice.getViolationTicketId());
                    if (ticketOpt.isPresent()) {
                        ViolationTicket ticket = ticketOpt.get();
                        ticket.setStatus("PAID");
                        violationTicketRepository.save(ticket);
                        zone = ticket.getEnvironmentalZone();
                    }
                }

                // Update Environmental Budget fine collections so report reflects payment
                String targetZone = (zone != null && !zone.isEmpty()) ? zone : "Zone 1";
                List<ProjectLeap34.project.Models.EnvironmentalBudget> budgets = environmentalBudgetRepository.findAll();
                ProjectLeap34.project.Models.EnvironmentalBudget matchedBudget = budgets.stream()
                        .filter(b -> b.getEnvironmentalZone() != null && targetZone.equalsIgnoreCase(b.getEnvironmentalZone()))
                        .findFirst().orElse(null);

                if (matchedBudget == null && !budgets.isEmpty()) {
                    matchedBudget = budgets.get(0);
                } else if (matchedBudget == null) {
                    matchedBudget = new ProjectLeap34.project.Models.EnvironmentalBudget();
                    matchedBudget.setBudgetName(targetZone + " Clean Air Fund");
                    matchedBudget.setEnvironmentalZone(targetZone);
                    matchedBudget.setBudgetAmount(100000.0);
                    matchedBudget.setActualExpenditure(25000.0);
                    matchedBudget.setFineCollections(0.0);
                    matchedBudget.setFiscalYear("2026-2027");
                }
                double current = matchedBudget.getFineCollections() != null ? matchedBudget.getFineCollections() : 0.0;
                Double payAmt = data.getAmount() != null ? data.getAmount() : 0.0;
                matchedBudget.setFineCollections(current + payAmt);
                environmentalBudgetRepository.save(matchedBudget);
            }
        }

        Double amount = data.getAmount() != null ? data.getAmount() : 0.0;
        journalEntryServices.createPaymentJournalEntry(savedPayment.getId(), amount);

        return convertToResponseDTO(savedPayment);
    }

    public List<PaymentResponseDTO> getallpayment() {
        return paymentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletebyid(Long Id) {
        paymentRepository.deleteById(Id);
    }

    public PaymentResponseDTO getbyid(Long Id) {
        Payment payment = paymentRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Payment Not Found"));
        return convertToResponseDTO(payment);
    }
}
