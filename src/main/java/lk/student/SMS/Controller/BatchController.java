package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.BatchDto;
import lk.student.SMS.Service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BatchDto> createBatch(@Valid @RequestBody BatchDto batchDto) {
        BatchDto created = batchService.createBatch(batchDto);
        return ResponseEntity.ok(created);
    }
    // Update an existing batch
    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<BatchDto> updateBatch(@PathVariable Long id, @Valid @RequestBody BatchDto batchDto) {
        BatchDto updated = batchService.updateBatch(id, batchDto);
        return ResponseEntity.ok(updated);
    }

    // Get a single batch by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BatchDto> getBatch(@PathVariable Long id) {
        BatchDto batchDto = batchService.getBatchById(id);
        return ResponseEntity.ok(batchDto);
    }

    // Get all batches
    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<BatchDto>> getAllBatches() {
        List<BatchDto> batches = batchService.getAllBatches();
        return ResponseEntity.ok(batches);
    }

    // Delete a batch
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COUNSELOR')")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }
}
