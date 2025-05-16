package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.CourseRepository;
import lk.student.SMS.Dao.FeeSchemeRepository;
import lk.student.SMS.Dto.CourseDto;
import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Entity.Course;
import lk.student.SMS.Entity.FeeScheme;
import lk.student.SMS.Exception.ResourceNotFoundException;
import lk.student.SMS.Service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final FeeSchemeRepository feeSchemeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             FeeSchemeRepository feeSchemeRepository,
                             ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.feeSchemeRepository = feeSchemeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        Course saved = courseRepository.save(course);
        return modelMapper.map(saved, CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());

        Course updated = courseRepository.save(course);
        return modelMapper.map(updated, CourseDto.class);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.map(course -> modelMapper.map(course, CourseDto.class));
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    @Override
    public List<FeeSchemeDto> getFeeSchemesByCourseId(Long courseId) {
        List<FeeScheme> feeSchemes = feeSchemeRepository.findByCourseId(courseId);

        modelMapper.typeMap(FeeScheme.class, FeeSchemeDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCreatedBy().getId(), FeeSchemeDto::setCreatedBy);
            mapper.map(src -> src.getCourse().getCourseId(), FeeSchemeDto::setCourseId);
        });

        return feeSchemes.stream()
                .map(feeScheme -> modelMapper.map(feeScheme, FeeSchemeDto.class))
                .toList();
    }
}
