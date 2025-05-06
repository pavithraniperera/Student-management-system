package lk.student.SMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "registeredBy")
    private List<Student> registeredStudents;
    // If the user is also a student
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student studentProfile;
    public boolean isCounselor() {
        return Role.COUNSELOR.equals(this.role);
    }
}
