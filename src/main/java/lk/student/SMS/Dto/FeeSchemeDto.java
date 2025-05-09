package lk.student.SMS.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FeeSchemeDto {
    private Long id;
    private String schemeName;
    private LocalDate createdDate;
    private String currency;
    private Long createdByUserId;
    private List<PaymentPlanDto> paymentPlans;
}
