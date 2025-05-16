package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.BatchRepository;
import lk.student.SMS.Dao.StudentRepository;
import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.BatchDto;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Dto.StudentDto;
import lk.student.SMS.Entity.Batch;
import lk.student.SMS.Entity.Student;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Enum.Role;
import lk.student.SMS.Exception.InvalidRequestException;
import lk.student.SMS.Exception.ResourceNotFoundException;
import lk.student.SMS.Service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException("Counselor not found with ID: " + studentDto.getRegisteredById()));

        if (!counselor.isCounselor()) {
            throw new InvalidRequestException("Only counselors can register students");
        }
        student.setRegisteredBy(counselor);

        Batch batch = batchRepository.findById(studentDto.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + studentDto.getBatchId()));
        student.setBatch(batch);

        if (studentDto.getUserId() != null) {
            User user = userRepository.findById(studentDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User account not found with ID: " + studentDto.getUserId()));
            student.setUser(user);
        } else {
            User newUser = new User();
            newUser.setEmail(studentDto.getEmail());
            newUser.setName(studentDto.getName());
            newUser.setPassword("123"); // Change to secure password strategy
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
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        Batch batch = student.getBatch();

        if (batch != null) {
            BatchDto batchDto = modelMapper.map(batch, BatchDto.class);

            if (batch.getIntake() != null) {
                IntakeDto intakeDto = modelMapper.map(batch.getIntake(), IntakeDto.class);
                batchDto.setIntake(intakeDto);
            }

            studentDto.setBatch(batchDto);
        }

        return studentDto;
    }

    @Override
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(student -> modelMapper.map(student, StudentDto.class));
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto dto) {


        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        if (dto.getBatchId() != null) {
            Batch batch = batchRepository.findById(dto.getBatchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + dto.getBatchId()));
            student.setBatch(batch);
        }

        if (dto.getRegisteredById() != null) {
            User registeredBy = userRepository.findById(dto.getRegisteredById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getRegisteredById()));

            if (!registeredBy.isCounselor()) {
                throw new InvalidRequestException("Only counselors can register students");
            }

            student.setRegisteredBy(registeredBy);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User account not found with ID: " + dto.getUserId()));
            student.setUser(user);
        } else {
            student.setUser(null);
        }

        Student updated = studentRepository.save(student);
        return modelMapper.map(updated, StudentDto.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        studentRepository.delete(student);
    }


}
