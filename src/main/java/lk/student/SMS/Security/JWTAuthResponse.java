package lk.student.SMS.Security;

import lombok.Builder;

@Builder
public class JWTAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
    private int status; // 1 = success, 0 = failure

    public JWTAuthResponse() {}

    public JWTAuthResponse(String accessToken,String refreshToken, String message, int status) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}