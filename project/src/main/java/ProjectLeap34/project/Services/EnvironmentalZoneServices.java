package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.EnvironmentalZoneRequestDTO;
import ProjectLeap34.project.DTO.EnvironmentalZoneResponseDTO;
import ProjectLeap34.project.Models.EnvironmentalZone;
import ProjectLeap34.project.Repository.EnvironmentalZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvironmentalZoneServices {

    @Autowired
    private EnvironmentalZoneRepository environmentalZoneRepository;

    private EnvironmentalZone convertToEntity(EnvironmentalZoneRequestDTO dto) {
        EnvironmentalZone zone = new EnvironmentalZone();
        zone.setZoneCode(dto.getZoneCode());
        zone.setZoneName(dto.getZoneName());
        zone.setPm25Limit(dto.getPm25Limit());
        zone.setCo2Limit(dto.getCo2Limit());
        zone.setDescription(dto.getDescription());
        zone.setActive(dto.getActive());
        return zone;
    }

    private EnvironmentalZoneResponseDTO convertToResponseDTO(EnvironmentalZone zone) {
        EnvironmentalZoneResponseDTO dto = new EnvironmentalZoneResponseDTO();
        dto.setId(zone.getId());
        dto.setZoneCode(zone.getZoneCode());
        dto.setZoneName(zone.getZoneName());
        dto.setPm25Limit(zone.getPm25Limit());
        dto.setCo2Limit(zone.getCo2Limit());
        dto.setDescription(zone.getDescription());
        dto.setActive(zone.getActive());
        return dto;
    }

    public EnvironmentalZoneResponseDTO createzone(EnvironmentalZoneRequestDTO data) {
        EnvironmentalZone zone = convertToEntity(data);
        EnvironmentalZone saved = environmentalZoneRepository.save(zone);
        return convertToResponseDTO(saved);
    }

    public List<EnvironmentalZoneResponseDTO> getallzone() {
        return environmentalZoneRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public EnvironmentalZoneResponseDTO updatezone(EnvironmentalZoneRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Zone ID is required for update");
        }
        EnvironmentalZone zone = environmentalZoneRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Environmental Zone Not Found"));
        if (data.getZoneCode() != null) zone.setZoneCode(data.getZoneCode());
        if (data.getZoneName() != null) zone.setZoneName(data.getZoneName());
        if (data.getPm25Limit() != null) zone.setPm25Limit(data.getPm25Limit());
        if (data.getCo2Limit() != null) zone.setCo2Limit(data.getCo2Limit());
        if (data.getDescription() != null) zone.setDescription(data.getDescription());
        if (data.getActive() != null) zone.setActive(data.getActive());
        EnvironmentalZone saved = environmentalZoneRepository.save(zone);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        environmentalZoneRepository.deleteById(Id);
    }

    public EnvironmentalZoneResponseDTO getbyid(Long Id) {
        EnvironmentalZone zone = environmentalZoneRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Environmental Zone Not Found"));
        return convertToResponseDTO(zone);
    }
}
