package lk.student.SMS.Service;

import lk.student.SMS.Dao.UserRepository;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Entity.User;
import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.SignIn;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, ModelMapper mapper, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userRepository.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not found"));
        var generatedToken = jwtService.generateToken(user);

        // Directly create JWTAuthResponse without using the builder
        return new JWTAuthResponse(generatedToken);
    }


    //save user in db and issue a token
    @Override
    public JWTAuthResponse signUp(UserDto userDTO) {
        // Convert the UserDto to a User entity
        User user = mapper.map(userDTO, User.class);

        // Save the user in the database
        User savedUser = userRepository.save(user);

        // Generate a JWT token for the saved user
        var token = jwtService.generateToken(savedUser);

        // Directly create JWTAuthResponse without using the builder
        return new JWTAuthResponse(token);
    }


    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        // Extract username from the existing token
        var userName = jwtService.extractUserName(accessToken);

        // Check if the user exists in the database
        var findUser = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("User Not found"));

        // Generate a refreshed token for the user
        var refreshedToken = jwtService.refreshToken(findUser);

        // Directly create JWTAuthResponse without using the builder
        return new JWTAuthResponse(refreshedToken);
    }

}