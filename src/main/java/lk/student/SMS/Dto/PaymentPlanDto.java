package lk.student.SMS.Dto;

import lombok.Data;

@Data
public class PaymentPlanDto {
    private String feeType;
    private String description;
    private double amount;
}
