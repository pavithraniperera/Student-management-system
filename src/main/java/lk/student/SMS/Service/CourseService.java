package lk.student.SMS.Service;

import lk.student.SMS.Dto.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    CourseDto getCourseById(Long id);
    List<CourseDto> getAllCourses();
    void deleteCourse(Long id);
}
