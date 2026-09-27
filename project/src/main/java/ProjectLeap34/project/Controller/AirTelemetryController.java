package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.AirTelemetryRequestDTO;
import ProjectLeap34.project.DTO.AirTelemetryResponseDTO;
import ProjectLeap34.project.Services.AirTelemetryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/air/telemetry/")
public class AirTelemetryController {

    @Autowired
    private AirTelemetryServices airTelemetryServices;

    @GetMapping("getall")
    ResponseEntity<List<AirTelemetryResponseDTO>> getall() {
        return new ResponseEntity<>(airTelemetryServices.getalltelemetry(), HttpStatus.OK);
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            AirTelemetryResponseDTO response = airTelemetryServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            airTelemetryServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<AirTelemetryResponseDTO> createtelemetry(@Valid @RequestBody AirTelemetryRequestDTO body) {
        return new ResponseEntity<>(airTelemetryServices.createtelemetry(body), HttpStatus.CREATED);
    }
}
