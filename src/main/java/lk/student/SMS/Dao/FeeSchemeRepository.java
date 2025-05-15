package lk.student.SMS.Dao;

import lk.student.SMS.Entity.FeeScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeSchemeRepository extends JpaRepository<FeeScheme, Long> {
    @Query("SELECT f FROM FeeScheme f WHERE f.course.courseId = :courseId")
    List<FeeScheme> findByCourseId(@Param("courseId") Long courseId);

}

