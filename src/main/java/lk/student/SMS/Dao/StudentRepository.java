package lk.student.SMS.Dao;

import lk.student.SMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  /*  List<Student> findByBatch_Id(Long batchId);  // Find students by batch ID

    // Find student by user ID
    Optional<Student> findByUser_UserId(Long userId);
    // Find student with batch and course details
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.batch b LEFT JOIN FETCH b.course WHERE s.studentId = :studentId")
    Optional<Student> findStudentWithDetails(Long studentId);*/
}
