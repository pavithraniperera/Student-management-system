package lk.student.SMS.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class BatchDto {
    private Long id;
    @NotBlank(message = "Batch name is required")
    private String batchName;

    @NotNull(message = "Intake ID is required")
    private Long intakeId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    public BatchDto() {
    }

    public BatchDto(Long id, String batchName, Long intakeId, Long courseId) {
        this.id = id;
        this.batchName = batchName;
        this.intakeId = intakeId;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Long intakeId) {
        this.intakeId = intakeId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
