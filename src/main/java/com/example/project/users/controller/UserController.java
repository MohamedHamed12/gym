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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.users.dto.GeneralResponse;
import com.example.project.users.dto.UserDTO;
import com.example.project.users.dto.UserFilterDTO;
import com.example.project.users.entity.User;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.UserService;
// import com.turkraft.springfilter.boot.Filter;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;



@RestController
@RequestMapping("/account")
// @CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi USER");
    }

    // @PreAuthorize("hasRole('USER')")
    // @PreAuthorize("hasAuthority('USER')")
    // @PreAuthorize("@MySecurityService.isUser(authentication, #resourceId,
    // ..anyOtherParametersIFpresent)")
    // @PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("@userService.isAuthorizedToDeleteUser(authentication.name,
    // #id)")

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_User')")

    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GeneralResponse("User deleted successfully"));

    }

    // @DeleteMapping("/users")
    // public ResponseEntity<String> deleteAllUsers() {

    //     userService.deleteAllUsers();
    //     return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All users deleted successfully");

    // }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_User')")

    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {

        User user = userService.saveUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("users/{id}")
    @PreAuthorize("hasRole('ROLE_User')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }



    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_User')")
    public ResponseEntity<Page<User>> getUsers(
            UserFilterDTO filterDTO,
            Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(filterDTO, pageable));
    }
   
}
