package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.VendorBillRequestDTO;
import ProjectLeap34.project.DTO.VendorBillResponseDTO;
import ProjectLeap34.project.Services.VendorBillServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendorbill/")
public class VendorBillController {

    @Autowired
    private VendorBillServices vendorBillServices;

    @GetMapping("getall")
    ResponseEntity<List<VendorBillResponseDTO>> getall() {
        return new ResponseEntity<>(vendorBillServices.getallbill(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatebill(@Valid @RequestBody VendorBillRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Vendor Bill ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(vendorBillServices.updatebill(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            VendorBillResponseDTO response = vendorBillServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            vendorBillServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<VendorBillResponseDTO> createbill(@Valid @RequestBody VendorBillRequestDTO body) {
        return new ResponseEntity<>(vendorBillServices.createbill(body), HttpStatus.CREATED);
    }

    @PostMapping("pay/{id}")
    ResponseEntity<?> payBillDemo(@PathVariable long id) {
        try {
            VendorBillResponseDTO response = vendorBillServices.payBillDemo(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }
}
