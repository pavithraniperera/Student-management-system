package lk.student.SMS.Dao;

import lk.student.SMS.Entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    Optional<PaymentPlan> findByFeeTypeAndAmountAndDescription(String feeType, Double amount, String description);

}