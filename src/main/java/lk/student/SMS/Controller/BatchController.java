package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.BatchDto;
import lk.student.SMS.Dto.MessageResponse;
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


            BatchDto created = batchService.createBatch(batchDto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);


    }
    // Update an existing batch
    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBatch(@PathVariable Long id, @Valid @RequestBody BatchDto batchDto) {

            BatchDto updated = batchService.updateBatch(id, batchDto);
            return new ResponseEntity<>(new MessageResponse("Batch updated successfully", 1), HttpStatus.OK);

    }


    // Get a batch by ID (available to all authenticated users)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBatch(@PathVariable Long id) {

            BatchDto batchDto = batchService.getBatchById(id);
            return new ResponseEntity<>(batchDto, HttpStatus.OK);

    }

    // Get all batches
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAllBatches() {

            List<BatchDto> batches = batchService.getAllBatches();
            return new ResponseEntity<>(batches, HttpStatus.OK);

    }

    // Delete a batch
    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Long id) {

            batchService.deleteBatch(id);
            return new ResponseEntity<>(new MessageResponse("Batch deleted successfully",1), HttpStatus.OK);

    }
}
