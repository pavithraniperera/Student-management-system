package lk.student.SMS.Service;

import lk.student.SMS.Dto.BatchDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BatchService {
    BatchDto createBatch(BatchDto batchDto);
    BatchDto updateBatch(Long id, BatchDto batchDto);
    ResponseEntity<?> deleteBatch(Long id);
    BatchDto getBatchById(Long id);
    List<BatchDto> getAllBatches();
}