package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.AccountRequestDTO;
import ProjectLeap34.project.DTO.AccountResponseDTO;
import ProjectLeap34.project.Services.AccountServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @GetMapping("getall")
    ResponseEntity<List<AccountResponseDTO>> getall() {
        return new ResponseEntity<>(accountServices.getallaccount(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updateaccount(@Valid @RequestBody AccountRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Account ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(accountServices.updateaccount(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            AccountResponseDTO response = accountServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            accountServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<AccountResponseDTO> createaccount(@Valid @RequestBody AccountRequestDTO body) {
        return new ResponseEntity<>(accountServices.createaccount(body), HttpStatus.CREATED);
    }
}
