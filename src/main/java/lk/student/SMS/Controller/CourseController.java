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

            CourseDto createdCourse = courseService.createCourse(courseDto);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {

            CourseDto updatedCourse = courseService.updateCourse(id, courseDto);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

            CourseDto course = courseService.getCourseById(id);
            return new ResponseEntity<>(course, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllCourses( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {


            Pageable pageable = PageRequest.of(page, size);
            Page<CourseDto> courses = courseService.getAllCourses(pageable);
            return new ResponseEntity<>(courses, HttpStatus.OK);

    }
    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {

            courseService.deleteCourse(id);
            return new ResponseEntity<>(new MessageResponse("Course deleted successfully", 1), HttpStatus.OK);

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<FeeSchemeDto>> getFeeSchemesByCourseId(@PathVariable Long courseId) {
        List<FeeSchemeDto> feeSchemes = courseService.getFeeSchemesByCourseId(courseId);
        return ResponseEntity.ok(feeSchemes);
    }



}

