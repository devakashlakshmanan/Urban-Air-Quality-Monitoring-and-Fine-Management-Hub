package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EnvironmentalZone {
    @Id
    @GeneratedValue
    Long Id;
    String ZoneCode;
    String ZoneName;
    Double Pm25Limit;
    Double Co2Limit;
    String Description;
    Boolean Active;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getZoneCode() { return ZoneCode; }
    public void setZoneCode(String zoneCode) { ZoneCode = zoneCode; }
    public String getZoneName() { return ZoneName; }
    public void setZoneName(String zoneName) { ZoneName = zoneName; }
    public Double getPm25Limit() { return Pm25Limit; }
    public void setPm25Limit(Double pm25Limit) { Pm25Limit = pm25Limit; }
    public Double getCo2Limit() { return Co2Limit; }
    public void setCo2Limit(Double co2Limit) { Co2Limit = co2Limit; }
    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }
    public Boolean getActive() { return Active; }
    public void setActive(Boolean active) { Active = active; }
}
