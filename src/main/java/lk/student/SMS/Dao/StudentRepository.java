package lk.student.SMS.Dao;

import lk.student.SMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByBatch_Id(Long batchId);  // Find students by batch ID
}
