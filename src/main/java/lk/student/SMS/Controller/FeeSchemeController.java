package lk.student.SMS.Controller;

import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Service.FeeSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeSchemes")
@PreAuthorize("hasAnyRole('FINANCE_MANAGER')")
public class FeeSchemeController {

    private final FeeSchemeService feeSchemeService;

    @Autowired
    public FeeSchemeController(FeeSchemeService feeSchemeService) {
        this.feeSchemeService = feeSchemeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FeeSchemeDto dto) {
        try {
            FeeSchemeDto createdFeeScheme = feeSchemeService.createFeeScheme(dto);
            return new ResponseEntity<>(createdFeeScheme, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating fee scheme: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            FeeSchemeDto feeScheme = feeSchemeService.getFeeSchemeById(id);
            return new ResponseEntity<>(feeScheme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving fee scheme: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<FeeSchemeDto> feeSchemes = feeSchemeService.getAll();
            return new ResponseEntity<>(feeSchemes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving fee schemes: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FeeSchemeDto dto) {
        try {
            FeeSchemeDto updatedFeeScheme = feeSchemeService.updateFeeScheme(id, dto);
            return new ResponseEntity<>(updatedFeeScheme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating fee scheme: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            feeSchemeService.deleteFeeScheme(id);
            return new ResponseEntity<>("Fee scheme deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting fee scheme: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
