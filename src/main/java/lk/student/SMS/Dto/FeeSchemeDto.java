package lk.student.SMS.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class FeeSchemeDto {
    private Long id;
    private String schemeName;
    private LocalDate createdDate;
    private String currency;
    private Long createdBy;
    private List<PaymentPlanDto> paymentPlans;

    public FeeSchemeDto() {
    }

    public FeeSchemeDto(Long id, String schemeName, LocalDate createdDate, String currency, Long createdBy, List<PaymentPlanDto> paymentPlans) {
        this.id = id;
        this.schemeName = schemeName;
        this.createdDate = createdDate;
        this.currency = currency;
        this.createdBy = createdBy;
        this.paymentPlans = paymentPlans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public List<PaymentPlanDto> getPaymentPlans() {
        return paymentPlans;
    }

    public void setPaymentPlans(List<PaymentPlanDto> paymentPlans) {
        this.paymentPlans = paymentPlans;
    }

    @Override
    public String toString() {
        return "FeeSchemeDto{" +
                "id=" + id +
                ", schemeName='" + schemeName + '\'' +
                ", createdDate=" + createdDate +
                ", currency='" + currency + '\'' +
                ", createdBy=" + createdBy +
                ", paymentPlans=" + paymentPlans +
                '}';
    }
}
