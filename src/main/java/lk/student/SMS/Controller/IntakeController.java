package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.IntakeDto;
import lk.student.SMS.Service.IntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<IntakeDto> createIntake(@Valid @RequestBody IntakeDto intakeDto) {
        return ResponseEntity.ok(intakeService.createIntake(intakeDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<IntakeDto> getIntakeById(@PathVariable Long id) {
        return ResponseEntity.ok(intakeService.getIntakeById(id));
    }

    @GetMapping
    public ResponseEntity<List<IntakeDto>> getAllIntakes() {
        return ResponseEntity.ok(intakeService.getAllIntakes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntakeDto> updateIntake(@PathVariable Long id, @Valid @RequestBody IntakeDto intakeDto) {
        return ResponseEntity.ok(intakeService.updateIntake(id, intakeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntake(@PathVariable Long id) {
        intakeService.deleteIntake(id);
        return ResponseEntity.noContent().build();
    }

}
