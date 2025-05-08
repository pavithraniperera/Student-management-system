package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.StudentDto;
import lk.student.SMS.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PreAuthorize("hasAnyRole('COUNSELOR')")
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
                                                    @Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

}
