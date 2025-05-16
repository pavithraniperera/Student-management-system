package lk.student.SMS.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lk.student.SMS.Dto.MessageResponse;
import lk.student.SMS.Dto.StudentDto;
import lk.student.SMS.Service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createStudent(
            @RequestParam("studentData") String studentData,  // Accept JSON as String
            @RequestParam("nicImage") MultipartFile nicImageFile) {


        try {
            // Manually convert JSON to StudentDto
            ObjectMapper objectMapper = new ObjectMapper();
            StudentDto studentDto = objectMapper.readValue(studentData, StudentDto.class);

            // Forward to service
            StudentDto createdStudent = studentService.createStudent(studentDto, nicImageFile);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);

        } catch (JsonProcessingException e) {
            // Handle JSON parsing errors
            return ResponseEntity.badRequest().body("Invalid student data format");
        }
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {

            StudentDto student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

            Pageable pageable = PageRequest.of(page, size);
            Page<StudentDto> students = studentService.getAllStudents(pageable);
            return new ResponseEntity<>(students, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {

            studentService.deleteStudent(id);
            // Return MessageResponse with success status
            return new ResponseEntity<>(new MessageResponse("Student deleted successfully", 1), HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestParam("studentData") String studentData,
            @RequestParam(value = "nicImage", required = false) MultipartFile nicImageFile) {

        try {
            StudentDto studentDto = new ObjectMapper().readValue(studentData, StudentDto.class);
            StudentDto updatedStudent = studentService.updateStudent(id, studentDto, nicImageFile);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid student data format");
        }
    }
}
