package lk.student.SMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;
    private String email;

    // Student is registered by one user (counselor)
    @ManyToOne
    @JoinColumn(name = "registered_by", nullable = false)
    private User registeredBy;

    // Student may have a user account (optional)
    @OneToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    // Student enrolls in one batch
    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @PrePersist //Runs before the entity is inserted (saved for the first time) into the database
    @PreUpdate //Runs before an existing entity is updated.
    private void validateRegistration() {
        if (registeredBy == null || !registeredBy.isCounselor()) {
            throw new IllegalStateException("Only counselors can register students");
        }
    }

    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
}
