package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userCreateDTO) {
       /* User user = mapper.map(userCreateDTO, User.class);
        return mapper.map(userRepository.save(user), UserDto.class);*/
        User user = new User();
       user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPhone(userCreateDTO.getPhone());
        user.setPassword(userCreateDTO.getPassword());
        user.setRole(userCreateDTO.getRole());

        User savedUser = userRepository.save(user);

        UserDto dto = new UserDto();
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        dto.setPhone(savedUser.getPhone());
        dto.setPassword(savedUser.getPassword());
        dto.setRole(savedUser.getRole());

        return dto;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
