package lk.student.SMS.Controller;

import lk.student.SMS.Dto.FeeSchemeDto;
import lk.student.SMS.Service.FeeSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeSchemes")
@PreAuthorize("hasAnyRole('FINANCE_MANAGER')")
public class FeeSchemeController {

    private final FeeSchemeService feeSchemeService;

    @Autowired
    public FeeSchemeController(FeeSchemeService feeSchemeService) {
        this.feeSchemeService = feeSchemeService;
    }

    @PostMapping
    public ResponseEntity<FeeSchemeDto> create(@RequestBody FeeSchemeDto dto) {
        System.out.println(dto);
        return ResponseEntity.ok(feeSchemeService.createFeeScheme(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeSchemeDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(feeSchemeService.getFeeSchemeById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeeSchemeDto>> getAll() {
        return ResponseEntity.ok(feeSchemeService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeSchemeDto> update(@PathVariable Long id, @RequestBody FeeSchemeDto dto) {
        return ResponseEntity.ok(feeSchemeService.updateFeeScheme(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feeSchemeService.deleteFeeScheme(id);
        return ResponseEntity.noContent().build();
    }
}
