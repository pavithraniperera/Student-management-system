package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Security.JWTAuthResponse;
import lk.student.SMS.Security.SignIn;
import lk.student.SMS.Service.AuthService;
import lk.student.SMS.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/auth")
@RestController
@CrossOrigin
public class AuthController {



    private final AuthService authService;
    private final UserService userService;

@Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> createUser(@Valid @RequestBody UserDto userDto) {
        System.out.println(userDto);
        try {
            // Register the user and issue a token
            JWTAuthResponse createdUserToken = authService.signUp(userDto);
            System.out.println(createdUserToken);
            return ResponseEntity.ok(createdUserToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  @PostMapping(value = "signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn) {
      return ResponseEntity.ok(authService.signIn(signIn));
  }

    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestParam("existingToken") String existingToken) {


        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }

}