package lk.student.SMS.Dto;

import lk.student.SMS.Entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role;
}

