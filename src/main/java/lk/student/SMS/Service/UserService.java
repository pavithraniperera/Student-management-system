package lk.student.SMS.Service;

import lk.student.SMS.Dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userCreateDTO);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    UserDetailsService userDetailService();
}
