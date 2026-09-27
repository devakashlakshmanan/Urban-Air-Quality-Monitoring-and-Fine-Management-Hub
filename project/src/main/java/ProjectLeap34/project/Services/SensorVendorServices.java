package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.SensorVendorRequestDTO;
import ProjectLeap34.project.DTO.SensorVendorResponseDTO;
import ProjectLeap34.project.Models.SensorVendor;
import ProjectLeap34.project.Repository.SensorVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorVendorServices {

    @Autowired
    private SensorVendorRepository sensorVendorRepository;

    private SensorVendor convertToEntity(SensorVendorRequestDTO dto) {
        SensorVendor vendor = new SensorVendor();
        vendor.setName(dto.getName());
        vendor.setVendorCode(dto.getVendorCode());
        vendor.setContactPerson(dto.getContactPerson());
        vendor.setPhone(dto.getPhone());
        vendor.setEmail(dto.getEmail());
        vendor.setAddress(dto.getAddress());
        vendor.setActive(dto.getActive());
        return vendor;
    }

    private SensorVendorResponseDTO convertToResponseDTO(SensorVendor vendor) {
        SensorVendorResponseDTO dto = new SensorVendorResponseDTO();
        dto.setId(vendor.getId());
        dto.setName(vendor.getName());
        dto.setVendorCode(vendor.getVendorCode());
        dto.setContactPerson(vendor.getContactPerson());
        dto.setPhone(vendor.getPhone());
        dto.setEmail(vendor.getEmail());
        dto.setAddress(vendor.getAddress());
        dto.setActive(vendor.getActive());
        return dto;
    }

    public SensorVendorResponseDTO createsensorvendor(SensorVendorRequestDTO data) {
        SensorVendor vendor = convertToEntity(data);
        SensorVendor saved = sensorVendorRepository.save(vendor);
        return convertToResponseDTO(saved);
    }

    public List<SensorVendorResponseDTO> getallsensorvendor() {
        return sensorVendorRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public SensorVendorResponseDTO updatesensorvendor(SensorVendorRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Sensor Vendor ID is required for update");
        }
        SensorVendor vendor = sensorVendorRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Sensor Vendor Not Found"));
        if (data.getName() != null) vendor.setName(data.getName());
        if (data.getVendorCode() != null) vendor.setVendorCode(data.getVendorCode());
        if (data.getContactPerson() != null) vendor.setContactPerson(data.getContactPerson());
        if (data.getPhone() != null) vendor.setPhone(data.getPhone());
        if (data.getEmail() != null) vendor.setEmail(data.getEmail());
        if (data.getAddress() != null) vendor.setAddress(data.getAddress());
        if (data.getActive() != null) vendor.setActive(data.getActive());
        SensorVendor saved = sensorVendorRepository.save(vendor);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        sensorVendorRepository.deleteById(Id);
    }

    public SensorVendorResponseDTO getbyid(Long Id) {
        SensorVendor vendor = sensorVendorRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Sensor Vendor Not Found"));
        return convertToResponseDTO(vendor);
    }
}
