package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.ViolationTicketRequestDTO;
import ProjectLeap34.project.DTO.ViolationTicketResponseDTO;
import ProjectLeap34.project.Services.ViolationTicketServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/violation/")
public class ViolationTicketController {

    @Autowired
    private ViolationTicketServices violationTicketServices;

    @GetMapping("getall")
    ResponseEntity<List<ViolationTicketResponseDTO>> getall() {
        return new ResponseEntity<>(violationTicketServices.getallviolation(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updateviolation(@Valid @RequestBody ViolationTicketRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Violation Ticket ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(violationTicketServices.updateviolation(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            ViolationTicketResponseDTO response = violationTicketServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            violationTicketServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<ViolationTicketResponseDTO> createviolation(@Valid @RequestBody ViolationTicketRequestDTO body) {
        return new ResponseEntity<>(violationTicketServices.createviolation(body), HttpStatus.CREATED);
    }
}
