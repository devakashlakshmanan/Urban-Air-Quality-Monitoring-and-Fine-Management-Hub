package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AirSensor {
    @Id
    @GeneratedValue
    Long Id;
    Long IndustrialPlantId;
    String SensorCode;
    String SensorName;
    String Location;
    String EnvironmentalZone;
    String Manufacturer;
    Double Pm25Threshold;
    Double Co2Threshold;
    String Status;
    String InstallationDate;
    Boolean Active;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getSensorCode() { return SensorCode; }
    public void setSensorCode(String sensorCode) { SensorCode = sensorCode; }
    public String getSensorName() { return SensorName; }
    public void setSensorName(String sensorName) { SensorName = sensorName; }
    public String getLocation() { return Location; }
    public void setLocation(String location) { Location = location; }
    public String getEnvironmentalZone() { return EnvironmentalZone; }
    public void setEnvironmentalZone(String environmentalZone) { EnvironmentalZone = environmentalZone; }
    public String getManufacturer() { return Manufacturer; }
    public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }
    public Double getPm25Threshold() { return Pm25Threshold; }
    public void setPm25Threshold(Double pm25Threshold) { Pm25Threshold = pm25Threshold; }
    public Double getCo2Threshold() { return Co2Threshold; }
    public void setCo2Threshold(Double co2Threshold) { Co2Threshold = co2Threshold; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
    public String getInstallationDate() { return InstallationDate; }
    public void setInstallationDate(String installationDate) { InstallationDate = installationDate; }
    public Boolean getActive() { return Active; }
    public void setActive(Boolean active) { Active = active; }
    public Long getIndustrialPlantId() { return IndustrialPlantId; }
    public void setIndustrialPlantId(Long industrialPlantId) { IndustrialPlantId = industrialPlantId; }
}
