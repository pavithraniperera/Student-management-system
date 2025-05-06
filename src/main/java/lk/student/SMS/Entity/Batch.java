package lk.student.SMS.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "batches")
@Data
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long batchId;

    private String batchName;


    // One batch belongs to one intake (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intake_id", nullable = false)
    private Intake intake;

    // One batch belongs to one course (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // One batch can have many students
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
