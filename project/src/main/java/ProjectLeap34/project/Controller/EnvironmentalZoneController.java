package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.EnvironmentalZoneRequestDTO;
import ProjectLeap34.project.DTO.EnvironmentalZoneResponseDTO;
import ProjectLeap34.project.Services.EnvironmentalZoneServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zone/")
public class EnvironmentalZoneController {

    @Autowired
    private EnvironmentalZoneServices environmentalZoneServices;

    @GetMapping("getall")
    ResponseEntity<List<EnvironmentalZoneResponseDTO>> getall() {
        return new ResponseEntity<>(environmentalZoneServices.getallzone(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatezone(@Valid @RequestBody EnvironmentalZoneRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Zone ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(environmentalZoneServices.updatezone(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            EnvironmentalZoneResponseDTO response = environmentalZoneServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            environmentalZoneServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<EnvironmentalZoneResponseDTO> createzone(@Valid @RequestBody EnvironmentalZoneRequestDTO body) {
        return new ResponseEntity<>(environmentalZoneServices.createzone(body), HttpStatus.CREATED);
    }
}
