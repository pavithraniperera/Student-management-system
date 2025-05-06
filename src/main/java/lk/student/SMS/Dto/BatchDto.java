package lk.student.SMS.Dto;

import lombok.Data;

@Data
public class BatchDto {
    private Long id;
    private String batchName;

    private Long intakeId;
    private Long courseId;
}
