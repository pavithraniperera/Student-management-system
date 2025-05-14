package lk.student.SMS.Service;

import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.SignIn;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<JWTAuthResponse> signIn(SignIn signIn);
    ResponseEntity<JWTAuthResponse> signUp(UserDto userDto);
}
