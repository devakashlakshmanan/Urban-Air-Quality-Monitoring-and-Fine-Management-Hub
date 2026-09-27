package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.EnvironmentalBudgetRequestDTO;
import ProjectLeap34.project.DTO.EnvironmentalBudgetResponseDTO;
import ProjectLeap34.project.Services.EnvironmentalBudgetServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget/")
public class EnvironmentalBudgetController {

    @Autowired
    private EnvironmentalBudgetServices environmentalBudgetServices;

    @GetMapping("getall")
    ResponseEntity<List<EnvironmentalBudgetResponseDTO>> getall() {
        return new ResponseEntity<>(environmentalBudgetServices.getallbudget(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatebudget(@Valid @RequestBody EnvironmentalBudgetRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Budget ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(environmentalBudgetServices.updatebudget(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            EnvironmentalBudgetResponseDTO response = environmentalBudgetServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            environmentalBudgetServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<EnvironmentalBudgetResponseDTO> createbudget(@Valid @RequestBody EnvironmentalBudgetRequestDTO body) {
        return new ResponseEntity<>(environmentalBudgetServices.createbudget(body), HttpStatus.CREATED);
    }

    @GetMapping("report/{id}")
    ResponseEntity<?> getBudgetReport(@PathVariable long id) {
        try {
            Map<String, Object> report = environmentalBudgetServices.getBudgetReport(id);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }
}
