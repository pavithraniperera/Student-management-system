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
        try {
            UserDto createdUser = userService.createUser(userCreateDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error creating user: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error retrieving user: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('COUNSELOR', 'FINANCE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error retrieving users: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('FINANCE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            // Return MessageResponse with success status
            return new ResponseEntity<>(new MessageResponse("User deleted successfully", 1), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error deleting user: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('COUNSELOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            // Return MessageResponse with failure status
            return new ResponseEntity<>(new MessageResponse("Error updating user: " + e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }
}
