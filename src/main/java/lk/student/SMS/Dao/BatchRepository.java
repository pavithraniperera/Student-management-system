package lk.student.SMS.Dao;

import lk.student.SMS.Entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByCourse_Id(Long courseId);  // Find batches by course ID
}