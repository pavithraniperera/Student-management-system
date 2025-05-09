package lk.student.SMS.Entity;

import jakarta.persistence.*;

@Entity
public class PaymentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feeType;
    private String description;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "fee_scheme_id")
    private FeeScheme feeScheme;

    // Getters and setters


    public PaymentPlan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public FeeScheme getFeeScheme() {
        return feeScheme;
    }

    public void setFeeScheme(FeeScheme feeScheme) {
        this.feeScheme = feeScheme;
    }
}
