package lk.student.SMS.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;




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

    public User() {
    }

    public User(Long id, String name, String email, String phone, String password, Role role, List<Student> registeredStudents, Student studentProfile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.registeredStudents = registeredStudents;
        this.studentProfile = studentProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public Student getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(Student studentProfile) {
        this.studentProfile = studentProfile;
    }

    @OneToMany(mappedBy = "registeredBy")
    private List<Student> registeredStudents;
    // If the user is also a student
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student studentProfile;
    public boolean isCounselor() {
        return Role.COUNSELOR.equals(this.role);
    }
}
