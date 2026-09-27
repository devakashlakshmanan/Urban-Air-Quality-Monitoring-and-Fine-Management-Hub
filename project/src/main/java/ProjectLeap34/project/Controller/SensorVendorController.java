package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.SensorVendorRequestDTO;
import ProjectLeap34.project.DTO.SensorVendorResponseDTO;
import ProjectLeap34.project.Services.SensorVendorServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensorvendor/")
public class SensorVendorController {

    @Autowired
    private SensorVendorServices sensorVendorServices;

    @GetMapping("getall")
    ResponseEntity<List<SensorVendorResponseDTO>> getall() {
        return new ResponseEntity<>(sensorVendorServices.getallsensorvendor(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatesensorvendor(@Valid @RequestBody SensorVendorRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Sensor Vendor ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(sensorVendorServices.updatesensorvendor(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            SensorVendorResponseDTO response = sensorVendorServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            sensorVendorServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<SensorVendorResponseDTO> createsensorvendor(@Valid @RequestBody SensorVendorRequestDTO body) {
        return new ResponseEntity<>(sensorVendorServices.createsensorvendor(body), HttpStatus.CREATED);
    }
}
