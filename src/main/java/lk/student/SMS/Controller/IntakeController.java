package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Service.IntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            IntakeDto createdIntake = intakeService.createIntake(intakeDto);
            return new ResponseEntity<>(createdIntake, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating intake: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getIntakeById(@PathVariable Long id) {
        try {
            IntakeDto intake = intakeService.getIntakeById(id);
            return new ResponseEntity<>(intake, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving intake: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllIntakes() {
        try {
            List<IntakeDto> intakes = intakeService.getAllIntakes();
            return new ResponseEntity<>(intakes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving intakes: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIntake(@PathVariable Long id, @Valid @RequestBody IntakeDto intakeDto) {
        try {
            IntakeDto updatedIntake = intakeService.updateIntake(id, intakeDto);
            return new ResponseEntity<>(updatedIntake, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating intake: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIntake(@PathVariable Long id) {
        try {
            intakeService.deleteIntake(id);
            return new ResponseEntity<>("Intake deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting intake: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

