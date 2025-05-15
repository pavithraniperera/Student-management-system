package lk.student.SMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name",nullable = false, unique = true)
    private String courseName;

    private String description;

    // A course can have many batches
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<FeeScheme> feeSchemes = new ArrayList<>();

    public List<FeeScheme> getFeeSchemes() {
        return feeSchemes;
    }

    public void setFeeSchemes(List<FeeScheme> feeSchemes) {
        this.feeSchemes = feeSchemes;
    }


    // Helper method to add a batch to this course
    public void addBatch(Batch batch) {
        batches.add(batch);
        batch.setCourse(this);
    }

    public Course() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
