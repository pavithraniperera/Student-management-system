package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.IntakeRepository;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Entity.Intake;
import lk.student.SMS.Service.IntakeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntakeServiceImpl implements IntakeService {
    private final IntakeRepository intakeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IntakeServiceImpl(IntakeRepository intakeRepository,ModelMapper modelMapper){
        this.intakeRepository = intakeRepository;
        this.modelMapper = modelMapper;

    }
    @Override
    public IntakeDto createIntake(IntakeDto intakeDto) {
        Intake intake = modelMapper.map(intakeDto, Intake.class);
        return modelMapper.map(intakeRepository.save(intake), IntakeDto.class);
    }

    @Override
    public IntakeDto getIntakeById(Long id) {
        Intake intake = intakeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intake not found"));
        return modelMapper.map(intake, IntakeDto.class);
    }

    @Override
    public List<IntakeDto> getAllIntakes() {
        return intakeRepository.findAll()
                .stream()
                .map(intake -> modelMapper.map(intake, IntakeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public IntakeDto updateIntake(Long id, IntakeDto intakeDto) {
        Intake intake = intakeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intake not found"));
        intake.setName(intakeDto.getName());
        intake.setStartDate(intakeDto.getStartDate());
        intake.setEndDate(intakeDto.getEndDate());

        return modelMapper.map(intakeRepository.save(intake), IntakeDto.class);

    }

    @Override
    public void deleteIntake(Long id) {
        intakeRepository.deleteById(id);
    }
}
