package com.example.project.users.dto;

import lombok.Getter;
import lombok.Setter;

import com.example.project.users.enums.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserFilterDTO {
    private String firstnameContains;  // "contains" filter for firstname
    private String firstnameStartsWith; // "starts with" filter for firstname
    private String lastnameContains;  // "contains" filter for lastname
    private String lastnameStartsWith; // "starts with" filter for lastname
    private String emailContains;     // "contains" filter for email
    private String emailStartsWith;   // "starts with" filter for email
    private Role role;                // exact match for role
    private Long idLessThan;           // "less than" filter for id
    private Long idGreaterThan;        // "greater than" filter for id
}