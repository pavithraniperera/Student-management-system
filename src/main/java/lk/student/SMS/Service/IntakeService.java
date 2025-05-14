package lk.student.SMS.Service;

import lk.student.SMS.Dto.IntakeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IntakeService {
    IntakeDto createIntake(IntakeDto intakeDto);
    IntakeDto getIntakeById(Long id);
    Page<IntakeDto> getAllIntakes(Pageable pageable);
    IntakeDto updateIntake(Long id, IntakeDto intakeDto);
    void deleteIntake(Long id);
}
