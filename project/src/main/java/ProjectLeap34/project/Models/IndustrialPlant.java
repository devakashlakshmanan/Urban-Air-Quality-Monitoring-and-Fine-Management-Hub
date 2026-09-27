package ProjectLeap34.project.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class IndustrialPlant {
    @Id
    @GeneratedValue
    Long Id;
    String Name;
    String PlantCode;
    String IndustryType;
    String Address;
    String ContactPerson;
    String Phone;
    String Email;
    String EnvironmentalZone;
    Boolean Active;

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }
    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
    public String getPlantCode() { return PlantCode; }
    public void setPlantCode(String plantCode) { PlantCode = plantCode; }
    public String getIndustryType() { return IndustryType; }
    public void setIndustryType(String industryType) { IndustryType = industryType; }
    public String getAddress() { return Address; }
    public void setAddress(String address) { Address = address; }
    public String getContactPerson() { return ContactPerson; }
    public void setContactPerson(String contactPerson) { ContactPerson = contactPerson; }
    public String getPhone() { return Phone; }
    public void setPhone(String phone) { Phone = phone; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
    public String getEnvironmentalZone() { return EnvironmentalZone; }
    public void setEnvironmentalZone(String environmentalZone) { EnvironmentalZone = environmentalZone; }
    public Boolean getActive() { return Active; }
    public void setActive(Boolean active) { Active = active; }
}
