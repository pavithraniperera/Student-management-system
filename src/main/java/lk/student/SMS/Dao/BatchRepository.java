package lk.student.SMS.Dao;

import lk.student.SMS.Entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByCourse_Id(Long courseId);  // Find batches by course ID
}