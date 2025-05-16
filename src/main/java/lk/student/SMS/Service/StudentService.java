package lk.student.SMS.Service;

import lk.student.SMS.Dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto, MultipartFile nicImageFile);
    StudentDto getStudentById(Long id);
    public Page<StudentDto> getAllStudents(Pageable pageable);

    StudentDto updateStudent(Long id, StudentDto studentDto, MultipartFile nicImageFile);
    void deleteStudent(Long id);
}
