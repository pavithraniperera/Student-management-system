package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.BatchRepository;
import lk.student.SMS.Dao.CourseRepository;
import lk.student.SMS.Dao.IntakeRepository;
import lk.student.SMS.Dto.BatchDto;
import lk.student.SMS.Entity.Batch;
import lk.student.SMS.Entity.Course;
import lk.student.SMS.Entity.Intake;
import lk.student.SMS.Exception.ResourceNotFoundException;
import lk.student.SMS.Service.BatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final IntakeRepository intakeRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BatchServiceImpl(
            BatchRepository batchRepository,
            IntakeRepository intakeRepository,
            CourseRepository courseRepository,
            ModelMapper modelMapper
    ) {
        this.batchRepository = batchRepository;
        this.intakeRepository = intakeRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BatchDto createBatch(BatchDto batchDto) {
        Intake intake = intakeRepository.findById(batchDto.getIntakeId())
                .orElseThrow(() -> new ResourceNotFoundException("Intake not found"));

        Course course = courseRepository.findById(batchDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Batch batch = new Batch();
        batch.setBatchName(batchDto.getBatchName());
        batch.setIntake(intake);
        batch.setCourse(course);

        Batch saved = batchRepository.save(batch);
        return modelMapper.map(saved, BatchDto.class);
    }

    @Override
    public BatchDto updateBatch(Long id, BatchDto batchDto) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        Intake intake = intakeRepository.findById(batchDto.getIntakeId())
                .orElseThrow(() -> new ResourceNotFoundException("Intake not found"));

        Course course = courseRepository.findById(batchDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        batch.setBatchName(batchDto.getBatchName());
        batch.setIntake(intake);
        batch.setCourse(course);

        Batch updated = batchRepository.save(batch);
        return modelMapper.map(updated, BatchDto.class);
    }

    @Override
    public void deleteBatch(Long id) {
        if (!batchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Batch not found");
        }
        batchRepository.deleteById(id);
    }

    @Override
    public BatchDto getBatchById(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
        return modelMapper.map(batch, BatchDto.class);
    }

    @Override
    public List<BatchDto> getAllBatches() {
        List<Batch> batches = batchRepository.findAll();
        List<BatchDto> batchDtos = new ArrayList<>();

        for (Batch batch : batches) {
            BatchDto dto = modelMapper.map(batch, BatchDto.class);
            batchDtos.add(dto);
        }

        return batchDtos;
    }
}
