package lk.student.SMS.Controller;

import jakarta.validation.Valid;
import lk.student.SMS.Dto.MessageResponse;
import lk.student.SMS.Dto.UserDto;
import lk.student.SMS.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userCreateDTO) {

            UserDto createdUser = userService.createUser(userCreateDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

            UserDto user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {

            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('FINANCE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

            userService.deleteUser(id);
            // Return MessageResponse with success status
            return new ResponseEntity<>(new MessageResponse("User deleted successfully", 1), HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {

            UserDto updatedUser = userService.updateUser(id, userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}
