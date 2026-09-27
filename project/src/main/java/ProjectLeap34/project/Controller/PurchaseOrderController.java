package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.PurchaseOrderRequestDTO;
import ProjectLeap34.project.DTO.PurchaseOrderResponseDTO;
import ProjectLeap34.project.Services.PurchaseOrderServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase/")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderServices purchaseOrderServices;

    @GetMapping("getall")
    ResponseEntity<List<PurchaseOrderResponseDTO>> getall() {
        return new ResponseEntity<>(purchaseOrderServices.getallpurchaseorder(), HttpStatus.OK);
    }

    @PutMapping({"update", "update/{id}"})
    public ResponseEntity<?> updatepurchaseorder(@Valid @RequestBody PurchaseOrderRequestDTO data, @PathVariable(required = false) Long id) {
        if (id != null) {
            data.setId(id);
        }
        if (data.getId() == null) {
            return new ResponseEntity<>("Purchase Order ID is required for update", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(purchaseOrderServices.updatepurchaseorder(data), HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            PurchaseOrderResponseDTO response = purchaseOrderServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            purchaseOrderServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<PurchaseOrderResponseDTO> createpurchaseorder(@Valid @RequestBody PurchaseOrderRequestDTO body) {
        return new ResponseEntity<>(purchaseOrderServices.createpurchaseorder(body), HttpStatus.CREATED);
    }
}
