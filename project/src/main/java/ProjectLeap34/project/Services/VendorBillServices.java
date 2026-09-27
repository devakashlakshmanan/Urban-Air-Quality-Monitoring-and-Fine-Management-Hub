package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.VendorBillRequestDTO;
import ProjectLeap34.project.DTO.VendorBillResponseDTO;
import ProjectLeap34.project.Models.PurchaseOrder;
import ProjectLeap34.project.Models.VendorBill;
import ProjectLeap34.project.Repository.PurchaseOrderRepository;
import ProjectLeap34.project.Repository.VendorBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorBillServices {

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private JournalEntryServices journalEntryServices;

    private VendorBill convertToEntity(VendorBillRequestDTO dto) {
        VendorBill bill = new VendorBill();
        bill.setBillNumber(dto.getBillNumber());
        bill.setPurchaseOrderId(dto.getPurchaseOrderId());
        bill.setVendorId(dto.getVendorId());
        bill.setBillDate(dto.getBillDate());
        bill.setAmount(dto.getAmount());
        bill.setStatus(dto.getStatus());
        return bill;
    }

    private VendorBillResponseDTO convertToResponseDTO(VendorBill bill) {
        VendorBillResponseDTO dto = new VendorBillResponseDTO();
        dto.setId(bill.getId());
        dto.setBillNumber(bill.getBillNumber());
        dto.setPurchaseOrderId(bill.getPurchaseOrderId());
        dto.setVendorId(bill.getVendorId());
        dto.setBillDate(bill.getBillDate());
        dto.setAmount(bill.getAmount());
        dto.setStatus(bill.getStatus());
        return dto;
    }

    public VendorBillResponseDTO createbill(VendorBillRequestDTO dto) {
        VendorBill data = convertToEntity(dto);
        if (data.getBillNumber() == null || data.getBillNumber().isEmpty()) {
            data.setBillNumber("BILL-" + System.currentTimeMillis());
        }
        if (data.getBillDate() == null || data.getBillDate().isEmpty()) {
            data.setBillDate(LocalDate.now().toString());
        }
        if (data.getStatus() == null || data.getStatus().isEmpty()) {
            data.setStatus("CREATED");
        }

        VendorBill savedBill = vendorBillRepository.save(data);

        if (data.getPurchaseOrderId() != null) {
            Optional<PurchaseOrder> poOpt = purchaseOrderRepository.findById(data.getPurchaseOrderId());
            if (poOpt.isPresent()) {
                PurchaseOrder po = poOpt.get();
                po.setStatus("BILLED");
                purchaseOrderRepository.save(po);
            }
        }

        Double amount = data.getAmount() != null ? data.getAmount() : 0.0;
        journalEntryServices.createVendorBillJournalEntry(savedBill.getId(), amount);

        return convertToResponseDTO(savedBill);
    }

    public List<VendorBillResponseDTO> getallbill() {
        return vendorBillRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public VendorBillResponseDTO updatebill(VendorBillRequestDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Vendor Bill ID is required for update");
        }
        VendorBill bill = vendorBillRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Vendor Bill Not Found"));
        if (dto.getBillNumber() != null) bill.setBillNumber(dto.getBillNumber());
        if (dto.getPurchaseOrderId() != null) bill.setPurchaseOrderId(dto.getPurchaseOrderId());
        if (dto.getVendorId() != null) bill.setVendorId(dto.getVendorId());
        if (dto.getBillDate() != null) bill.setBillDate(dto.getBillDate());
        if (dto.getAmount() != null) bill.setAmount(dto.getAmount());
        if (dto.getStatus() != null) bill.setStatus(dto.getStatus());
        VendorBill saved = vendorBillRepository.save(bill);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        vendorBillRepository.deleteById(Id);
    }

    public VendorBillResponseDTO getbyid(Long Id) {
        VendorBill bill = vendorBillRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Vendor Bill Not Found"));
        return convertToResponseDTO(bill);
    }

    public VendorBillResponseDTO payBillDemo(Long id) {
        VendorBill bill = vendorBillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor Bill Not Found"));
        bill.setStatus("PAID");
        VendorBill saved = vendorBillRepository.save(bill);
        Double amount = bill.getAmount() != null ? bill.getAmount() : 0.0;
        journalEntryServices.createVendorPaymentJournalEntry(bill.getId(), amount);
        return convertToResponseDTO(saved);
    }
}
