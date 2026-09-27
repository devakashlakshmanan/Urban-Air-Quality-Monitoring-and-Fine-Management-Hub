package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.AirSensorRequestDTO;
import ProjectLeap34.project.DTO.AirSensorResponseDTO;
import ProjectLeap34.project.Services.AirSensorServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/air/sensors/")
public class AirSensorController {

    @Autowired
    private AirSensorServices airSensorServices;

    @GetMapping("getall")
    ResponseEntity<List<AirSensorResponseDTO>> getall() {
        return new ResponseEntity<>(airSensorServices.getallsensor(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatesensor(@Valid @RequestBody AirSensorRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Air Sensor ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(airSensorServices.updatesensor(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            AirSensorResponseDTO response = airSensorServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            airSensorServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<AirSensorResponseDTO> createsensor(@Valid @RequestBody AirSensorRequestDTO body) {
        return new ResponseEntity<>(airSensorServices.createsensor(body), HttpStatus.CREATED);
    }
}
