package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Entity.User;

import lk.student.SMS.Security.JwtUtil;
import lk.student.SMS.Security.SignIn;
import lk.student.SMS.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String signIn(SignIn signIn) {
        // authenticate the provided credentials using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        // If authentication passes, fetch the user from the database
        User user = userRepository.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user);
        // Generate and return a JWT token for the user
        return jwtUtil.generateToken(user);
    }

    @Override
    public String signUp(UserDto userDto) {
        // Check if a user with the same email already exists
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setName(user.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        userRepository.save(user);
        // Generate and return a JWT token for the newly registered user
        return jwtUtil.generateToken(user);
    }
}
