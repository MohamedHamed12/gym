package com.example.project.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.users.dto.GeneralResponse;
import com.example.project.users.dto.UserDTO;
import com.example.project.users.entity.User;
import com.example.project.users.service.UserService;

import jakarta.validation.Valid;



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

    // @PreAuthorize("hasRole('USER')")
        // @PreAuthorize("hasAuthority('USER')")
    // @PreAuthorize("@MySecurityService.isUser(authentication, #resourceId, ..anyOtherParametersIFpresent)")
// @PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("@userService.isAuthorizedToDeleteUser(authentication.name, #id)")

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

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {

        User user = userService.saveUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


       @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

       @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
