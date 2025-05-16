package lk.student.SMS.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class StudentDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Batch ID is required")
    private Long batchId;

    @NotNull(message = "RegisteredBy ID is required")
    private Long registeredById;

    private Long userId; // optional

    private BatchDto batch; // optional
    private String nicImageBase64; // Base64 string of the image


    public StudentDto() {
    }

    public StudentDto(Long id, String name, String email, Long batchId, Long registeredById, Long userId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.batchId = batchId;
        this.registeredById = registeredById;
        this.userId = userId;
    }

    public StudentDto(Long id, String name, String email, Long batchId, Long registeredById, Long userId, BatchDto batch, IntakeDto intake) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.batchId = batchId;
        this.registeredById = registeredById;
        this.userId = userId;
        this.batch = batch;

    }

    public StudentDto(Long id, String name, String email, Long batchId, Long registeredById, Long userId, BatchDto batch, String nicImageBase64) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.batchId = batchId;
        this.registeredById = registeredById;
        this.userId = userId;
        this.batch = batch;
        this.nicImageBase64 = nicImageBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getRegisteredById() {
        return registeredById;
    }

    public void setRegisteredById(Long registeredById) {
        this.registeredById = registeredById;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BatchDto getBatch() {
        return batch;
    }

    public void setBatch(BatchDto batch) {
        this.batch = batch;
    }

    public String getNicImageBase64() {
        return nicImageBase64;
    }

    public void setNicImageBase64(String nicImageBase64) {
        this.nicImageBase64 = nicImageBase64;
    }
}

