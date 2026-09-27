package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ViolationTicket {
    @Id
    @GeneratedValue
    Long Id;
    String TicketNumber;
    Long IndustrialPlantId;
    Long SensorId;
    Long TelemetryId;
    String EnvironmentalZone;
    String Parameter;
    Double MeasuredValue;
    Double ThresholdValue;
    String ViolationDate;
    Double PenaltyAmount;
    String Status;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getTicketNumber() { return TicketNumber; }
    public void setTicketNumber(String ticketNumber) { TicketNumber = ticketNumber; }
    public Long getIndustrialPlantId() { return IndustrialPlantId; }
    public void setIndustrialPlantId(Long industrialPlantId) { IndustrialPlantId = industrialPlantId; }
    public Long getSensorId() { return SensorId; }
    public void setSensorId(Long sensorId) { SensorId = sensorId; }
    public Long getTelemetryId() { return TelemetryId; }
    public void setTelemetryId(Long telemetryId) { TelemetryId = telemetryId; }
    public String getEnvironmentalZone() { return EnvironmentalZone; }
    public void setEnvironmentalZone(String environmentalZone) { EnvironmentalZone = environmentalZone; }
    public String getParameter() { return Parameter; }
    public void setParameter(String parameter) { Parameter = parameter; }
    public Double getMeasuredValue() { return MeasuredValue; }
    public void setMeasuredValue(Double measuredValue) { MeasuredValue = measuredValue; }
    public Double getThresholdValue() { return ThresholdValue; }
    public void setThresholdValue(Double thresholdValue) { ThresholdValue = thresholdValue; }
    public String getViolationDate() { return ViolationDate; }
    public void setViolationDate(String violationDate) { ViolationDate = violationDate; }
    public Double getPenaltyAmount() { return PenaltyAmount; }
    public void setPenaltyAmount(Double penaltyAmount) { PenaltyAmount = penaltyAmount; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}
