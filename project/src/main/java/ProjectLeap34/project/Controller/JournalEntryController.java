package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.JournalEntryRequestDTO;
import ProjectLeap34.project.DTO.JournalEntryResponseDTO;
import ProjectLeap34.project.Services.JournalEntryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal/")
public class JournalEntryController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @GetMapping("getall")
    ResponseEntity<List<JournalEntryResponseDTO>> getall() {
        return new ResponseEntity<>(journalEntryServices.getalljournalentry(), HttpStatus.OK);
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            JournalEntryResponseDTO response = journalEntryServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<JournalEntryResponseDTO> createjournalentry(@Valid @RequestBody JournalEntryRequestDTO body) {
        return new ResponseEntity<>(journalEntryServices.createjournalentry(body), HttpStatus.CREATED);
    }
}
