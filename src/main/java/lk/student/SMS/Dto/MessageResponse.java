package lk.student.SMS.Dto;

public class MessageResponse {
    private String message;
    private int status;  // Add status field (1 for success, 0 for failure)

    // Constructor
    public MessageResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
