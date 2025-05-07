package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.CourseRepository;
import lk.student.SMS.Dto.CourseDto;
import lk.student.SMS.Entity.Course;
import lk.student.SMS.Service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    private  CourseRepository courseRepository;
    private  ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        return null;
    }

    @Override
    public CourseDto getCourseById(Long id) {
        return null;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return null;
    }

    @Override
    public void deleteCourse(Long id) {

    }
}
