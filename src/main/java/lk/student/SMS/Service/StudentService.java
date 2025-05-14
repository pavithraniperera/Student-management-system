package lk.student.SMS.Service;

import lk.student.SMS.Dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    StudentDto getStudentById(Long id);
    public Page<StudentDto> getAllStudents(Pageable pageable);

    StudentDto updateStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);
}
