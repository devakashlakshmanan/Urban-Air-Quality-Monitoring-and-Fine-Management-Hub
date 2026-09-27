package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.IndustrialPlantRequestDTO;
import ProjectLeap34.project.DTO.IndustrialPlantResponseDTO;
import ProjectLeap34.project.Models.IndustrialPlant;
import ProjectLeap34.project.Repository.IndustrialPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndustrialPlantServices {

    @Autowired
    private IndustrialPlantRepository industrialPlantRepository;

    private IndustrialPlant convertToEntity(IndustrialPlantRequestDTO dto) {
        IndustrialPlant plant = new IndustrialPlant();
        plant.setName(dto.getName());
        plant.setPlantCode(dto.getPlantCode());
        plant.setIndustryType(dto.getIndustryType());
        plant.setAddress(dto.getAddress());
        plant.setContactPerson(dto.getContactPerson());
        plant.setPhone(dto.getPhone());
        plant.setEmail(dto.getEmail());
        plant.setEnvironmentalZone(dto.getEnvironmentalZone());
        plant.setActive(dto.getActive());
        return plant;
    }

    private IndustrialPlantResponseDTO convertToResponseDTO(IndustrialPlant plant) {
        IndustrialPlantResponseDTO dto = new IndustrialPlantResponseDTO();
        dto.setId(plant.getId());
        dto.setName(plant.getName());
        dto.setPlantCode(plant.getPlantCode());
        dto.setIndustryType(plant.getIndustryType());
        dto.setAddress(plant.getAddress());
        dto.setContactPerson(plant.getContactPerson());
        dto.setPhone(plant.getPhone());
        dto.setEmail(plant.getEmail());
        dto.setEnvironmentalZone(plant.getEnvironmentalZone());
        dto.setActive(plant.getActive());
        return dto;
    }

    public IndustrialPlantResponseDTO createIndustrialPlant(IndustrialPlantRequestDTO data) {
        IndustrialPlant plant = convertToEntity(data);
        IndustrialPlant saved = industrialPlantRepository.save(plant);
        return convertToResponseDTO(saved);
    }

    public List<IndustrialPlantResponseDTO> getallindustrialplant() {
        return industrialPlantRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public IndustrialPlantResponseDTO updateindustrialplant(IndustrialPlantRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Plant ID is required for update");
        }
        IndustrialPlant plant = industrialPlantRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Industrial Plant Not Found"));
        if (data.getName() != null) plant.setName(data.getName());
        if (data.getPlantCode() != null) plant.setPlantCode(data.getPlantCode());
        if (data.getIndustryType() != null) plant.setIndustryType(data.getIndustryType());
        if (data.getAddress() != null) plant.setAddress(data.getAddress());
        if (data.getContactPerson() != null) plant.setContactPerson(data.getContactPerson());
        if (data.getPhone() != null) plant.setPhone(data.getPhone());
        if (data.getEmail() != null) plant.setEmail(data.getEmail());
        if (data.getEnvironmentalZone() != null) plant.setEnvironmentalZone(data.getEnvironmentalZone());
        if (data.getActive() != null) plant.setActive(data.getActive());
        IndustrialPlant saved = industrialPlantRepository.save(plant);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        industrialPlantRepository.deleteById(Id);
    }

    public IndustrialPlantResponseDTO getbyid(Long Id) {
        IndustrialPlant plant = industrialPlantRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Industrial Plant Not Found"));
        return convertToResponseDTO(plant);
    }
}
