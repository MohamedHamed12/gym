package com.example.project.users.dto;



import jakarta.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}
