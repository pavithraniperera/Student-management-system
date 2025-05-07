package lk.student.SMS.Service;

import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDto userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}