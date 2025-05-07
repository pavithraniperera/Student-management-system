package lk.student.SMS.Service;

import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.SignIn;

public interface AuthService {
    String signIn(SignIn signIn);
    String signUp(UserDto userDto);
}
