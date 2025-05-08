package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.BatchRepository;
import lk.student.SMS.Dao.StudentRepository;
import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.StudentDto;
import lk.student.SMS.Entity.Batch;
import lk.student.SMS.Entity.Role;
import lk.student.SMS.Entity.Student;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final BatchRepository batchRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRepository userRepository,
                              BatchRepository batchRepository,
                              ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.batchRepository = batchRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = new Student();

        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());

        User counselor = userRepository.findById(studentDto.getRegisteredById())
                .orElseThrow(() -> new RuntimeException("Counselor not found"));

        if (!counselor.isCounselor()) {
            throw new IllegalStateException("Only counselors can register students");
        }
        student.setRegisteredBy(counselor);

        Batch batch = batchRepository.findById(studentDto.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        student.setBatch(batch);

        if (studentDto.getUserId() != null) {
            User user = userRepository.findById(studentDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User account not found"));
            student.setUser(user);
        } else {
            // Optional: auto-create a user account with STUDENT role
            User newUser = new User();
            newUser.setEmail(studentDto.getEmail());
            newUser.setName(studentDto.getName());
            newUser.setPassword("123"); // or generate one
            newUser.setRole(Role.STUDENT);
            userRepository.save(newUser);
            student.setUser(newUser);
        }


        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDto.class);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();

        for (Student student : students) {
            StudentDto dto = modelMapper.map(student, StudentDto.class);
            studentDtos.add(dto);
        }

        return studentDtos;

    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        if (dto.getBatchId() != null) {
            Batch batch = batchRepository.findById(dto.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found"));
            student.setBatch(batch);
        }

        if (dto.getRegisteredById() != null) {
            User registeredBy = userRepository.findById(dto.getRegisteredById())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (!registeredBy.isCounselor()) {
                throw new IllegalStateException("Only counselors can register students");
            }
            student.setRegisteredBy(registeredBy);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User account not found"));
            student.setUser(user);
        } else {
            student.setUser(null); // remove user link if null provided
        }

        Student updated = studentRepository.save(student);
        return modelMapper.map(updated, StudentDto.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);
    }
}
