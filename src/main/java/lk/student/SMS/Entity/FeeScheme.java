package lk.student.SMS.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FeeScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schemeName;
    private LocalDate createdDate;
    private String currency;

    @OneToMany(mappedBy = "feeScheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentPlan> paymentPlans = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Getters and setters
}
