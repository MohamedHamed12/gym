package com.example.project.users.dto;





import com.example.project.users.Annotation.UniqueEmail;
import com.example.project.users.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    // private long id;
    
    @NotBlank(message = "First name is mandatory")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    private String lastname;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
    
    private Role role=Role.User;

    private Boolean emailConfirmed=false;

}
