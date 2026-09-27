package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.CustomerInvoiceRequestDTO;
import ProjectLeap34.project.DTO.CustomerInvoiceResponseDTO;
import ProjectLeap34.project.Services.CustomerInvoiceServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice/")
public class CustomerInvoiceController {

    @Autowired
    private CustomerInvoiceServices customerInvoiceServices;

    @GetMapping("getall")
    ResponseEntity<List<CustomerInvoiceResponseDTO>> getall() {
        return new ResponseEntity<>(customerInvoiceServices.getallinvoice(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updateinvoice(@Valid @RequestBody CustomerInvoiceRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Invoice ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(customerInvoiceServices.updateinvoice(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            CustomerInvoiceResponseDTO response = customerInvoiceServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            customerInvoiceServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<CustomerInvoiceResponseDTO> createinvoice(@Valid @RequestBody CustomerInvoiceRequestDTO body) {
        return new ResponseEntity<>(customerInvoiceServices.createinvoice(body), HttpStatus.CREATED);
    }
}
