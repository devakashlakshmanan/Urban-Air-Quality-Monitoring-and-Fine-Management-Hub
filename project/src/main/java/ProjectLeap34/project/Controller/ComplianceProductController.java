package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.ComplianceProductRequestDTO;
import ProjectLeap34.project.DTO.ComplianceProductResponseDTO;
import ProjectLeap34.project.Services.ComplianceProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/")
public class ComplianceProductController {

    @Autowired
    private ComplianceProductServices complianceProductServices;

    @GetMapping("getall")
    ResponseEntity<List<ComplianceProductResponseDTO>> getall() {
        return new ResponseEntity<>(complianceProductServices.getallproduct(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updateproduct(@Valid @RequestBody ComplianceProductRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Product ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(complianceProductServices.updateproduct(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            ComplianceProductResponseDTO response = complianceProductServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            complianceProductServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<ComplianceProductResponseDTO> createproduct(@Valid @RequestBody ComplianceProductRequestDTO body) {
        return new ResponseEntity<>(complianceProductServices.createproduct(body), HttpStatus.CREATED);
    }
}
