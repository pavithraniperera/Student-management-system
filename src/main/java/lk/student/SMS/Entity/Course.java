package lk.student.SMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(nullable = false, unique = true)
    private String courseName;

    private String description;

    // A course can have many batches
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();


    // Helper method to add a batch to this course
    public void addBatch(Batch batch) {
        batches.add(batch);
        batch.setCourse(this);
    }
}
