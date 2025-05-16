package lk.student.SMS.Controller;

import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.JwtUtil;
import lk.student.SMS.Security.SignIn;
import lk.student.SMS.Service.AuthService;
import lk.student.SMS.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil,UserService userService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;

    }



    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody SignIn signIn) {
        return authService.signIn(signIn);
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody UserDto userDto) {
        return authService.signUp(userDto);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        try {
            // Extract token from "Bearer <token>"
            String refreshToken = refreshTokenHeader.replace("Bearer ", "");

            // Extract username from the refresh token
            String username = jwtUtil.extractUserName(refreshToken);

            // Check if the refresh token is expired
            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(401).body(
                        new JWTAuthResponse(null, null, "Refresh token expired", 0));
            }

            // Load user details
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Generate a new access token
            String newAccessToken = jwtUtil.generateToken(userDetails);

            // Optionally, generate a new refresh token (not required but optional best practice)
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Return both tokens
            return ResponseEntity.ok(
                    new JWTAuthResponse(newAccessToken, newRefreshToken, "Token refreshed successfully", 1));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                    new JWTAuthResponse(null, null, "Failed to refresh token: " + e.getMessage(), 0));
        }
    }

}
