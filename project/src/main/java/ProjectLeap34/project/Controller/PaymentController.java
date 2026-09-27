package ProjectLeap34.project.Controller;

import ProjectLeap34.project.DTO.PaymentRequestDTO;
import ProjectLeap34.project.DTO.PaymentResponseDTO;
import ProjectLeap34.project.Services.PaymentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment/")
public class PaymentController {

    @Autowired
    private PaymentServices paymentServices;

    @GetMapping("getall")
    ResponseEntity<List<PaymentResponseDTO>> getall() {
        return new ResponseEntity<>(paymentServices.getallpayment(), HttpStatus.OK);
    }

    @GetMapping("getbyid/{id}")
    ResponseEntity<?> getbyId(@PathVariable long id) {
        try {
            PaymentResponseDTO response = paymentServices.getbyid(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            paymentServices.deletebyid(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    ResponseEntity<PaymentResponseDTO> createpayment(@Valid @RequestBody PaymentRequestDTO body) {
        return new ResponseEntity<>(paymentServices.createpayment(body), HttpStatus.CREATED);
    }
}
