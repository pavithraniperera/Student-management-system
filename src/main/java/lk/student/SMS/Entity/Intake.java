package lk.student.SMS.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intakes")
@Data
public class Intake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intake_id")
    private Long intakeId;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    // One intake can have many batches
    @OneToMany(mappedBy = "intake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();

    // Helper method to add a batch
    public void addBatch(Batch batch) {
        batches.add(batch);
        batch.setIntake(this);
    }
}
