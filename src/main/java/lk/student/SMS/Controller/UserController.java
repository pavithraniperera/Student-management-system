package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Create a new user
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userCreateDTO) {
        System.out.println(userCreateDTO);
        UserDto createdUser = userService.createUser(userCreateDTO);
        return ResponseEntity.ok(createdUser);
    }
}
