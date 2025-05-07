package lk.student.SMS.Controller;

import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.SignIn;
import lk.student.SMS.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignIn signIn) {
        String response = authService.signIn(signIn);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        String response = authService.signUp(userDto);
        return ResponseEntity.ok(response);
    }
}
