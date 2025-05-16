package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Exception.ResourceNotFoundException;
import lk.student.SMS.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userCreateDTO) {
        User user = mapper.map(userCreateDTO, User.class);
        return mapper.map(userRepository.save(user), UserDto.class);



    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        UserDto userDto= mapper.map(userOpt.get(), UserDto.class);
        // Set password to null
        userDto.setPassword(null);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto dto = mapper.map(user, UserDto.class);
            // Set password to null
            dto.setPassword(null);
            userDtos.add(dto);
        }

        return userDtos;

    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);

    }
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Map the updated fields (only update specific fields you allow to be updated)
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setRole(userDto.getRole());

        User updatedUser = userRepository.save(existingUser);

        return mapper.map(updatedUser, UserDto.class);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
