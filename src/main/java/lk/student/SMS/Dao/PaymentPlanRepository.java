package lk.student.SMS.Dao;

import lk.student.SMS.Entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    Optional<PaymentPlan> findByFeeTypeAndAmountAndDescription(String feeType, Double amount, String description);

}