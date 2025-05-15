package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.MessageResponse;
import lk.student.SMS.Dto.StudentDto;
import lk.student.SMS.Service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasAnyRole('COUNSELOR')")
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDto studentDto) {
        try {
            StudentDto createdStudent = studentService.createStudent(studentDto);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error creating student: " + e.getMessage(), 0), HttpStatus.OK);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        try {
            StudentDto student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error retrieving student: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<StudentDto> students = studentService.getAllStudents(pageable);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error retrieving students: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            // Return MessageResponse with success status
            return new ResponseEntity<>(new MessageResponse("Student deleted successfully", 1), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error deleting student: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        try {
            StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error updating student: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }
}
