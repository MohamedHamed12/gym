package com.example.project.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.users.dto.GeneralResponse;
import com.example.project.users.service.UserService;



@RestController
@RequestMapping("/account")
// @CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi USER");
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
     
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GeneralResponse("User deleted successfully"));
     
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAllUsers() {
        
            userService.deleteAllUsers();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All users deleted successfully");
       
    }
}
