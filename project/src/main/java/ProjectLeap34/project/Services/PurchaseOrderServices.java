package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.PurchaseOrderRequestDTO;
import ProjectLeap34.project.DTO.PurchaseOrderResponseDTO;
import ProjectLeap34.project.Models.PurchaseOrder;
import ProjectLeap34.project.Repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServices {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    private PurchaseOrder convertToEntity(PurchaseOrderRequestDTO dto) {
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderNumber(dto.getPurchaseOrderNumber());
        order.setVendorId(dto.getVendorId());
        order.setProductId(dto.getProductId());
        order.setOrderDate(dto.getOrderDate());
        order.setQuantity(dto.getQuantity());
        order.setAmount(dto.getAmount());
        order.setStatus(dto.getStatus());
        return order;
    }

    private PurchaseOrderResponseDTO convertToResponseDTO(PurchaseOrder order) {
        PurchaseOrderResponseDTO dto = new PurchaseOrderResponseDTO();
        dto.setId(order.getId());
        dto.setPurchaseOrderNumber(order.getPurchaseOrderNumber());
        dto.setVendorId(order.getVendorId());
        dto.setProductId(order.getProductId());
        dto.setOrderDate(order.getOrderDate());
        dto.setQuantity(order.getQuantity());
        dto.setAmount(order.getAmount());
        dto.setStatus(order.getStatus());
        return dto;
    }

    public PurchaseOrderResponseDTO createpurchaseorder(PurchaseOrderRequestDTO dto) {
        PurchaseOrder data = convertToEntity(dto);
        if (data.getPurchaseOrderNumber() == null || data.getPurchaseOrderNumber().isEmpty()) {
            data.setPurchaseOrderNumber("PO-" + System.currentTimeMillis());
        }
        if (data.getOrderDate() == null || data.getOrderDate().isEmpty()) {
            data.setOrderDate(LocalDate.now().toString());
        }
        if (data.getStatus() == null || data.getStatus().isEmpty()) {
            data.setStatus("CREATED");
        }
        PurchaseOrder saved = purchaseOrderRepository.save(data);
        return convertToResponseDTO(saved);
    }

    public List<PurchaseOrderResponseDTO> getallpurchaseorder() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public PurchaseOrderResponseDTO updatepurchaseorder(PurchaseOrderRequestDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Purchase Order ID is required for update");
        }
        PurchaseOrder order = purchaseOrderRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Purchase Order Not Found"));
        if (dto.getPurchaseOrderNumber() != null) order.setPurchaseOrderNumber(dto.getPurchaseOrderNumber());
        if (dto.getVendorId() != null) order.setVendorId(dto.getVendorId());
        if (dto.getProductId() != null) order.setProductId(dto.getProductId());
        if (dto.getOrderDate() != null) order.setOrderDate(dto.getOrderDate());
        if (dto.getQuantity() != null) order.setQuantity(dto.getQuantity());
        if (dto.getAmount() != null) order.setAmount(dto.getAmount());
        if (dto.getStatus() != null) order.setStatus(dto.getStatus());
        PurchaseOrder saved = purchaseOrderRepository.save(order);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        purchaseOrderRepository.deleteById(Id);
    }

    public PurchaseOrderResponseDTO getbyid(Long Id) {
        PurchaseOrder order = purchaseOrderRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Purchase Order Not Found"));
        return convertToResponseDTO(order);
    }
}
