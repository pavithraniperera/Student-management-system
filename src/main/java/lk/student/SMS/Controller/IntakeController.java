package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Service.IntakeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<IntakeDto> createIntake(@Valid @RequestBody IntakeDto intakeDto) {
        return ResponseEntity.ok(intakeService.createIntake(intakeDto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<IntakeDto> getIntakeById(@PathVariable Long id) {
        return ResponseEntity.ok(intakeService.getIntakeById(id));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<IntakeDto>> getAllIntakes() {
        return ResponseEntity.ok(intakeService.getAllIntakes());
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<IntakeDto> updateIntake(@PathVariable Long id, @Valid @RequestBody IntakeDto intakeDto) {
        return ResponseEntity.ok(intakeService.updateIntake(id, intakeDto));
    }
    @PreAuthorize("hasRole('COUNSELOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntake(@PathVariable Long id) {
        intakeService.deleteIntake(id);
        return ResponseEntity.noContent().build();
    }

}
