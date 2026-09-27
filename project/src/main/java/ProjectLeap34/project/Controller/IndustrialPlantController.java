package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.IndustrialPlantRequestDTO;
import ProjectLeap34.project.DTO.IndustrialPlantResponseDTO;
import ProjectLeap34.project.Services.IndustrialPlantServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industrialplant/")
public class IndustrialPlantController {

    @Autowired
    private IndustrialPlantServices industrialPlantServices;

    @GetMapping("getall")
    ResponseEntity<List<IndustrialPlantResponseDTO>> getall() {
        return new ResponseEntity<>(industrialPlantServices.getallindustrialplant(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updateindustrialplant(@Valid @RequestBody IndustrialPlantRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Plant ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(industrialPlantServices.updateindustrialplant(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            IndustrialPlantResponseDTO response = industrialPlantServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            industrialPlantServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<IndustrialPlantResponseDTO> createIndustrialPlant(@Valid @RequestBody IndustrialPlantRequestDTO body) {
        return new ResponseEntity<>(industrialPlantServices.createIndustrialPlant(body), HttpStatus.CREATED);
    }
}
