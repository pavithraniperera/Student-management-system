package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Dto.MessageResponse;
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
    public ResponseEntity<?> create(@Valid @RequestBody FeeSchemeDto dto) {
        try {
            FeeSchemeDto createdFeeScheme = feeSchemeService.createFeeScheme(dto);
            return new ResponseEntity<>(createdFeeScheme, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error creating fee scheme: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {

            FeeSchemeDto feeScheme = feeSchemeService.getFeeSchemeById(id);
            return new ResponseEntity<>(feeScheme, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAll() {

            List<FeeSchemeDto> feeSchemes = feeSchemeService.getAll();
            return new ResponseEntity<>(feeSchemes, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody FeeSchemeDto dto) {

            FeeSchemeDto updatedFeeScheme = feeSchemeService.updateFeeScheme(id, dto);
            return new ResponseEntity<>(updatedFeeScheme, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

            feeSchemeService.deleteFeeScheme(id);
            // Return MessageResponse with success status
            return new ResponseEntity<>(new MessageResponse("Fee scheme deleted successfully", 1), HttpStatus.NO_CONTENT);

    }
}
