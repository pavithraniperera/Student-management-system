package lk.student.SMS.Service;

import lk.student.SMS.Dto.CourseDto;
import lk.student.SMS.Dto.FeeSchemeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    CourseDto getCourseById(Long id);
    Page<CourseDto> getAllCourses(Pageable pageable);

    void deleteCourse(Long id);
    List<FeeSchemeDto> getFeeSchemesByCourseId(Long courseId);

}
