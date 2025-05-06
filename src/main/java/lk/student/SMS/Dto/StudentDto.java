package lk.student.SMS.Dto;

import lombok.Data;

@Data
public class StudentDto {
    private Long id;
    private String name;
    private String email;
    private Long batchId;
    private Long registeredById;  // Counselor user ID
    private Long userId; // optional
}

