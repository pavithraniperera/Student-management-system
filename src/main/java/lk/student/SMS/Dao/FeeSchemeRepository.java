package lk.student.SMS.Dao;

import lk.student.SMS.Entity.FeeScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeSchemeRepository extends JpaRepository<FeeScheme, Long> {}

