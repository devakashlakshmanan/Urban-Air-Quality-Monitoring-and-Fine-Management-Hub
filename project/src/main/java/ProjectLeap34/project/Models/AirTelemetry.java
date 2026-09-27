package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AirTelemetry {
    @Id
    @GeneratedValue
    Long Id;
    Long SensorId;
    Double Pm25;
    Double Co2;
    String RecordedAt;
    String EnvironmentalZone;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public Long getSensorId() { return SensorId; }
    public void setSensorId(Long sensorId) { SensorId = sensorId; }
    public Double getPm25() { return Pm25; }
    public void setPm25(Double pm25) { Pm25 = pm25; }
    public Double getCo2() { return Co2; }
    public void setCo2(Double co2) { Co2 = co2; }
    public String getRecordedAt() { return RecordedAt; }
    public void setRecordedAt(String recordedAt) { RecordedAt = recordedAt; }
    public String getEnvironmentalZone() { return EnvironmentalZone; }
    public void setEnvironmentalZone(String environmentalZone) { EnvironmentalZone = environmentalZone; }
}
