package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Dto.MessageResponse;
import lk.student.SMS.Service.IntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/intakes")
public class IntakeController {

    private final IntakeService intakeService;

    @Autowired
    public IntakeController(IntakeService intakeService) {
        this.intakeService = intakeService;
    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> createIntake(@Valid @RequestBody IntakeDto intakeDto) {

            IntakeDto createdIntake = intakeService.createIntake(intakeDto);
            return new ResponseEntity<>(createdIntake, HttpStatus.CREATED);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getIntakeById(@PathVariable Long id) {

            IntakeDto intake = intakeService.getIntakeById(id);
            return new ResponseEntity<>(intake, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllIntakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

            Pageable pageable = PageRequest.of(page, size);
            Page<IntakeDto> intakes = intakeService.getAllIntakes(pageable);
            return new ResponseEntity<>(intakes, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIntake(@PathVariable Long id, @Valid @RequestBody IntakeDto intakeDto) {

            IntakeDto updatedIntake = intakeService.updateIntake(id, intakeDto);
            return new ResponseEntity<>(updatedIntake, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIntake(@PathVariable Long id) {

            intakeService.deleteIntake(id);

            return new ResponseEntity<>(new MessageResponse("Intake deleted successfully", 1), HttpStatus.NO_CONTENT);

    }
}
