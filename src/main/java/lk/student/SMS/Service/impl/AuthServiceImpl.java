package lk.student.SMS.Service.impl;

import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Entity.User;

import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.JwtUtil;
import lk.student.SMS.Security.SignIn;
import lk.student.SMS.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<JWTAuthResponse> signIn(SignIn signIn) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));

            User user = userRepository.findByEmail(signIn.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(user);
            JWTAuthResponse response = new JWTAuthResponse(token, "Login successful", 1);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            JWTAuthResponse response = new JWTAuthResponse(null, "Login failed: " + e.getMessage(), 0);
            return ResponseEntity.ok(response);
        }
    }



    @Override
    public ResponseEntity<JWTAuthResponse> signUp(UserDto userDto) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.ok(new JWTAuthResponse(null, "User already exists", 0));
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(userDto.getRole());

            userRepository.save(user);

            String token = jwtUtil.generateToken(user);
            JWTAuthResponse response = new JWTAuthResponse(token, "User registered successfully", 1);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            JWTAuthResponse response = new JWTAuthResponse(null, "Registration failed: " + e.getMessage(), 0);
            return ResponseEntity.ok(response);
        }
    }

}
