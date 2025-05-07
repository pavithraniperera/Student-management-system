package lk.student.SMS.Service;

import lk.student.SMS.Dto.IntakeDto;

import java.util.List;

public interface IntakeService {
    IntakeDto createIntake(IntakeDto intakeDto);
    IntakeDto getIntakeById(Long id);
    List<IntakeDto> getAllIntakes();
    IntakeDto updateIntake(Long id, IntakeDto intakeDto);
    void deleteIntake(Long id);
}
