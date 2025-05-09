package lk.student.SMS.Dto;

import lombok.Data;


public class PaymentPlanDto {
    private String feeType;
    private String description;
    private double amount;

    public PaymentPlanDto() {
    }

    public PaymentPlanDto(String feeType, String description, double amount) {
        this.feeType = feeType;
        this.description = description;
        this.amount = amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

