package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.CourseDto;
import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Dto.MessageResponse;
import lk.student.SMS.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto createdCourse = courseService.createCourse(courseDto);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( new MessageResponse("Error Creating course: " + e.getMessage(),0), HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto updatedCourse = courseService.updateCourse(id, courseDto);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error updating course: " + e.getMessage(),0), HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            CourseDto course = courseService.getCourseById(id);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error retrieving course: " + e.getMessage(),0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllCourses( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CourseDto> courses = courseService.getAllCourses(pageable);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error retrieving courses: " + e.getMessage(),0), HttpStatus.BAD_REQUEST);

        }
    }
    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(new MessageResponse("Course deleted successfully", 1), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse("Course not found", 0), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error deleting course: " + e.getMessage(), 0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<FeeSchemeDto>> getFeeSchemesByCourseId(@PathVariable Long courseId) {
        List<FeeSchemeDto> feeSchemes = courseService.getFeeSchemesByCourseId(courseId);
        return ResponseEntity.ok(feeSchemes);
    }



}

