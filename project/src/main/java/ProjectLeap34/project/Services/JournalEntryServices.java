package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.JournalEntryRequestDTO;
import ProjectLeap34.project.DTO.JournalEntryResponseDTO;
import ProjectLeap34.project.Models.JournalEntry;
import ProjectLeap34.project.Models.Account;
import ProjectLeap34.project.Repository.AccountRepository;
import ProjectLeap34.project.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AccountRepository accountRepository;

    private void updateAccountBalance(String accountCode, Double debit, Double credit) {
        if (debit == null) debit = 0.0;
        if (credit == null) credit = 0.0;
        
        Account account = accountRepository.findAll().stream()
                .filter(a -> accountCode.equals(a.getAccountCode()))
                .findFirst().orElse(null);
        
        if (account != null) {
            Double currentBalance = account.getBalance() != null ? account.getBalance() : 0.0;
            String type = account.getAccountType() != null ? account.getAccountType().toUpperCase() : "";
            
            if (type.equals("ASSET") || type.equals("EXPENSE")) {
                account.setBalance(currentBalance + debit - credit);
            } else if (type.equals("LIABILITY") || type.equals("INCOME")) {
                account.setBalance(currentBalance + credit - debit);
            }
            accountRepository.save(account);
        }
    }

    private JournalEntry convertToEntity(JournalEntryRequestDTO dto) {
        JournalEntry entry = new JournalEntry();
        entry.setReferenceNumber(dto.getReferenceNumber());
        entry.setEntryDate(dto.getEntryDate());
        entry.setAccountCode(dto.getAccountCode());
        entry.setDescription(dto.getDescription());
        entry.setDebit(dto.getDebit());
        entry.setCredit(dto.getCredit());
        entry.setReferenceType(dto.getReferenceType());
        entry.setReferenceId(dto.getReferenceId());
        return entry;
    }

    private JournalEntryResponseDTO convertToResponseDTO(JournalEntry entry) {
        JournalEntryResponseDTO dto = new JournalEntryResponseDTO();
        dto.setId(entry.getId());
        dto.setReferenceNumber(entry.getReferenceNumber());
        dto.setEntryDate(entry.getEntryDate());
        dto.setAccountCode(entry.getAccountCode());
        dto.setDescription(entry.getDescription());
        dto.setDebit(entry.getDebit());
        dto.setCredit(entry.getCredit());
        dto.setReferenceType(entry.getReferenceType());
        dto.setReferenceId(entry.getReferenceId());
        return dto;
    }

    public JournalEntryResponseDTO createjournalentry(JournalEntryRequestDTO dto) {
        JournalEntry data = convertToEntity(dto);
        JournalEntry saved = journalEntryRepository.save(data);
        return convertToResponseDTO(saved);
    }

    public List<JournalEntryResponseDTO> getalljournalentry() {
        return journalEntryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public JournalEntryResponseDTO getbyid(Long Id) {
        JournalEntry entry = journalEntryRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Journal Entry Not Found"));
        return convertToResponseDTO(entry);
    }

    public void createFineJournalEntry(Long referenceId, Double amount) {
        String refNo = "JE-FINE-" + System.currentTimeMillis();
        String dateStr = LocalDate.now().toString();

        JournalEntry debitEntry = new JournalEntry();
        debitEntry.setReferenceNumber(refNo);
        debitEntry.setEntryDate(dateStr);
        debitEntry.setAccountCode("1200");
        debitEntry.setDescription("Accounts Receivable - Industrial Plant Fine");
        debitEntry.setDebit(amount);
        debitEntry.setCredit(0.0);
        debitEntry.setReferenceType("VIOLATION");
        debitEntry.setReferenceId(referenceId);
        journalEntryRepository.save(debitEntry);
        updateAccountBalance(debitEntry.getAccountCode(), debitEntry.getDebit(), debitEntry.getCredit());

        JournalEntry creditEntry = new JournalEntry();
        creditEntry.setReferenceNumber(refNo);
        creditEntry.setEntryDate(dateStr);
        creditEntry.setAccountCode("4100");
        creditEntry.setDescription("Environmental Fines Revenue");
        creditEntry.setDebit(0.0);
        creditEntry.setCredit(amount);
        creditEntry.setReferenceType("VIOLATION");
        creditEntry.setReferenceId(referenceId);
        journalEntryRepository.save(creditEntry);
        updateAccountBalance(creditEntry.getAccountCode(), creditEntry.getDebit(), creditEntry.getCredit());
    }

    public void createPaymentJournalEntry(Long referenceId, Double amount) {
        String refNo = "JE-PAY-" + System.currentTimeMillis();
        String dateStr = LocalDate.now().toString();

        JournalEntry debitEntry = new JournalEntry();
        debitEntry.setReferenceNumber(refNo);
        debitEntry.setEntryDate(dateStr);
        debitEntry.setAccountCode("1000");
        debitEntry.setDescription("Cash/Bank - Fine Payment Receipt");
        debitEntry.setDebit(amount);
        debitEntry.setCredit(0.0);
        debitEntry.setReferenceType("PAYMENT");
        debitEntry.setReferenceId(referenceId);
        journalEntryRepository.save(debitEntry);
        updateAccountBalance(debitEntry.getAccountCode(), debitEntry.getDebit(), debitEntry.getCredit());

        JournalEntry creditEntry = new JournalEntry();
        creditEntry.setReferenceNumber(refNo);
        creditEntry.setEntryDate(dateStr);
        creditEntry.setAccountCode("1200");
        creditEntry.setDescription("Accounts Receivable - Industrial Plant Settled");
        creditEntry.setDebit(0.0);
        creditEntry.setCredit(amount);
        creditEntry.setReferenceType("PAYMENT");
        creditEntry.setReferenceId(referenceId);
        journalEntryRepository.save(creditEntry);
        updateAccountBalance(creditEntry.getAccountCode(), creditEntry.getDebit(), creditEntry.getCredit());
    }

    public void createVendorBillJournalEntry(Long referenceId, Double amount) {
        String refNo = "JE-BILL-" + System.currentTimeMillis();
        String dateStr = LocalDate.now().toString();

        JournalEntry debitEntry = new JournalEntry();
        debitEntry.setReferenceNumber(refNo);
        debitEntry.setEntryDate(dateStr);
        debitEntry.setAccountCode("5100");
        debitEntry.setDescription("Sensor Calibration and Maintenance Expense");
        debitEntry.setDebit(amount);
        debitEntry.setCredit(0.0);
        debitEntry.setReferenceType("VENDOR_BILL");
        debitEntry.setReferenceId(referenceId);
        journalEntryRepository.save(debitEntry);
        updateAccountBalance(debitEntry.getAccountCode(), debitEntry.getDebit(), debitEntry.getCredit());

        JournalEntry creditEntry = new JournalEntry();
        creditEntry.setReferenceNumber(refNo);
        creditEntry.setEntryDate(dateStr);
        creditEntry.setAccountCode("2100");
        creditEntry.setDescription("Sensor Vendor Creditors");
        creditEntry.setDebit(0.0);
        creditEntry.setCredit(amount);
        creditEntry.setReferenceType("VENDOR_BILL");
        creditEntry.setReferenceId(referenceId);
        journalEntryRepository.save(creditEntry);
        updateAccountBalance(creditEntry.getAccountCode(), creditEntry.getDebit(), creditEntry.getCredit());
    }

    public void createVendorPaymentJournalEntry(Long referenceId, Double amount) {
        String refNo = "JE-VPAY-" + System.currentTimeMillis();
        String dateStr = LocalDate.now().toString();

        JournalEntry debitEntry = new JournalEntry();
        debitEntry.setReferenceNumber(refNo);
        debitEntry.setEntryDate(dateStr);
        debitEntry.setAccountCode("2100");
        debitEntry.setDescription("Sensor Vendor Creditors Settled");
        debitEntry.setDebit(amount);
        debitEntry.setCredit(0.0);
        debitEntry.setReferenceType("VENDOR_PAYMENT");
        debitEntry.setReferenceId(referenceId);
        journalEntryRepository.save(debitEntry);
        updateAccountBalance(debitEntry.getAccountCode(), debitEntry.getDebit(), debitEntry.getCredit());

        JournalEntry creditEntry = new JournalEntry();
        creditEntry.setReferenceNumber(refNo);
        creditEntry.setEntryDate(dateStr);
        creditEntry.setAccountCode("1000");
        creditEntry.setDescription("Cash/Bank - Vendor Settlement");
        creditEntry.setDebit(0.0);
        creditEntry.setCredit(amount);
        creditEntry.setReferenceType("VENDOR_PAYMENT");
        creditEntry.setReferenceId(referenceId);
        journalEntryRepository.save(creditEntry);
        updateAccountBalance(creditEntry.getAccountCode(), creditEntry.getDebit(), creditEntry.getCredit());
    }
}
