package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.BatchDto;
import lk.student.SMS.Service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }
    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> createBatch(@Valid @RequestBody BatchDto batchDto) {

        try {
            BatchDto created = batchService.createBatch(batchDto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return error details along with a custom message
            return new ResponseEntity<>("Error creating batch: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    // Update an existing batch
    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBatch(@PathVariable Long id, @Valid @RequestBody BatchDto batchDto) {
        try {
            BatchDto updated = batchService.updateBatch(id, batchDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating batch: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Get a batch by ID (available to all authenticated users)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBatch(@PathVariable Long id) {
        try {
            BatchDto batchDto = batchService.getBatchById(id);
            return new ResponseEntity<>(batchDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving batch: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all batches
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAllBatches() {
        try {
            List<BatchDto> batches = batchService.getAllBatches();
            return new ResponseEntity<>(batches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving batches: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a batch
    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Long id) {
        try {
            batchService.deleteBatch(id);
            return new ResponseEntity<>("Batch deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting batch: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
