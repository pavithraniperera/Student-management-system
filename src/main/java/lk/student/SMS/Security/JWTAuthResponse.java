package lk.student.SMS.Security;

import lombok.Builder;

@Builder
public class JWTAuthResponse {
    private String token;
    private String message;
    private int status; // 1 = success, 0 = failure

    public JWTAuthResponse() {}

    public JWTAuthResponse(String token, String message, int status) {
        this.token = token;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}