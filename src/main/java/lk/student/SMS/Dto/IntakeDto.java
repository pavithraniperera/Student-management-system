package lk.student.SMS.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IntakeDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
