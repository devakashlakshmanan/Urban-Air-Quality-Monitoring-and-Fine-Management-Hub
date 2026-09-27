package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.ComplianceProductRequestDTO;
import ProjectLeap34.project.DTO.ComplianceProductResponseDTO;
import ProjectLeap34.project.Models.ComplianceProduct;
import ProjectLeap34.project.Repository.ComplianceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceProductServices {

    @Autowired
    private ComplianceProductRepository complianceProductRepository;

    private ComplianceProduct convertToEntity(ComplianceProductRequestDTO dto) {
        ComplianceProduct product = new ComplianceProduct();
        product.setName(dto.getName());
        product.setCode(dto.getCode());
        product.setProductType(dto.getProductType());
        product.setDescription(dto.getDescription());
        product.setUnitPrice(dto.getUnitPrice());
        product.setActive(dto.getActive());
        return product;
    }

    private ComplianceProductResponseDTO convertToResponseDTO(ComplianceProduct product) {
        ComplianceProductResponseDTO dto = new ComplianceProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCode(product.getCode());
        dto.setProductType(product.getProductType());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setActive(product.getActive());
        return dto;
    }

    public ComplianceProductResponseDTO createproduct(ComplianceProductRequestDTO data) {
        ComplianceProduct product = convertToEntity(data);
        ComplianceProduct saved = complianceProductRepository.save(product);
        return convertToResponseDTO(saved);
    }

    public List<ComplianceProductResponseDTO> getallproduct() {
        return complianceProductRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ComplianceProductResponseDTO updateproduct(ComplianceProductRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Product ID is required for update");
        }
        ComplianceProduct product = complianceProductRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Compliance Product Not Found"));
        if (data.getName() != null) product.setName(data.getName());
        if (data.getCode() != null) product.setCode(data.getCode());
        if (data.getProductType() != null) product.setProductType(data.getProductType());
        if (data.getDescription() != null) product.setDescription(data.getDescription());
        if (data.getUnitPrice() != null) product.setUnitPrice(data.getUnitPrice());
        if (data.getActive() != null) product.setActive(data.getActive());
        ComplianceProduct saved = complianceProductRepository.save(product);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        complianceProductRepository.deleteById(Id);
    }

    public ComplianceProductResponseDTO getbyid(Long Id) {
        ComplianceProduct product = complianceProductRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        return convertToResponseDTO(product);
    }
}
