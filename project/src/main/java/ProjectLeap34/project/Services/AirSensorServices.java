package ProjectLeap34.project.Services;

import ProjectLeap34.project.DTO.AirSensorRequestDTO;
import ProjectLeap34.project.DTO.AirSensorResponseDTO;
import ProjectLeap34.project.Models.AirSensor;
import ProjectLeap34.project.Repository.AirSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirSensorServices {

    @Autowired
    private AirSensorRepository airSensorRepository;

    private AirSensor convertToEntity(AirSensorRequestDTO dto) {
        AirSensor sensor = new AirSensor();
        sensor.setIndustrialPlantId(dto.getIndustrialPlantId());
        sensor.setSensorCode(dto.getSensorCode());
        sensor.setSensorName(dto.getSensorName());
        sensor.setLocation(dto.getLocation());
        sensor.setEnvironmentalZone(dto.getEnvironmentalZone());
        sensor.setManufacturer(dto.getManufacturer());
        sensor.setPm25Threshold(dto.getPm25Threshold());
        sensor.setCo2Threshold(dto.getCo2Threshold());
        sensor.setStatus(dto.getStatus());
        sensor.setInstallationDate(dto.getInstallationDate());
        sensor.setActive(dto.getActive());
        return sensor;
    }

    private AirSensorResponseDTO convertToResponseDTO(AirSensor sensor) {
        AirSensorResponseDTO dto = new AirSensorResponseDTO();
        dto.setId(sensor.getId());
        dto.setIndustrialPlantId(sensor.getIndustrialPlantId());
        dto.setSensorCode(sensor.getSensorCode());
        dto.setSensorName(sensor.getSensorName());
        dto.setLocation(sensor.getLocation());
        dto.setEnvironmentalZone(sensor.getEnvironmentalZone());
        dto.setManufacturer(sensor.getManufacturer());
        dto.setPm25Threshold(sensor.getPm25Threshold());
        dto.setCo2Threshold(sensor.getCo2Threshold());
        dto.setStatus(sensor.getStatus());
        dto.setInstallationDate(sensor.getInstallationDate());
        dto.setActive(sensor.getActive());
        return dto;
    }

    public AirSensorResponseDTO createsensor(AirSensorRequestDTO data) {
        AirSensor sensor = convertToEntity(data);
        AirSensor saved = airSensorRepository.save(sensor);
        return convertToResponseDTO(saved);
    }

    public List<AirSensorResponseDTO> getallsensor() {
        return airSensorRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AirSensorResponseDTO updatesensor(AirSensorRequestDTO data) {
        if (data.getId() == null) {
            throw new RuntimeException("Air Sensor ID is required for update");
        }
        AirSensor sensor = airSensorRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Air Sensor Not Found"));
        if (data.getIndustrialPlantId() != null) sensor.setIndustrialPlantId(data.getIndustrialPlantId());
        if (data.getSensorCode() != null) sensor.setSensorCode(data.getSensorCode());
        if (data.getSensorName() != null) sensor.setSensorName(data.getSensorName());
        if (data.getLocation() != null) sensor.setLocation(data.getLocation());
        if (data.getEnvironmentalZone() != null) sensor.setEnvironmentalZone(data.getEnvironmentalZone());
        if (data.getManufacturer() != null) sensor.setManufacturer(data.getManufacturer());
        if (data.getPm25Threshold() != null) sensor.setPm25Threshold(data.getPm25Threshold());
        if (data.getCo2Threshold() != null) sensor.setCo2Threshold(data.getCo2Threshold());
        if (data.getStatus() != null) sensor.setStatus(data.getStatus());
        if (data.getInstallationDate() != null) sensor.setInstallationDate(data.getInstallationDate());
        if (data.getActive() != null) sensor.setActive(data.getActive());
        AirSensor saved = airSensorRepository.save(sensor);
        return convertToResponseDTO(saved);
    }

    public void deletebyid(Long Id) {
        airSensorRepository.deleteById(Id);
    }

    public AirSensorResponseDTO getbyid(Long Id) {
        AirSensor sensor = airSensorRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Air Sensor Not Found"));
        return convertToResponseDTO(sensor);
    }
}
