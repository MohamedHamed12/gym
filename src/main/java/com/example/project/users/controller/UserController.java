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

    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }



    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(
            UserFilterDTO filterDTO,
            Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(filterDTO, pageable));
    }
    // public Page<User> getAllUsers(
    //     @And({
    //         @Spec(path = "firstname", params = "firstnameContains", spec = Like.class),
    //         @Spec(path = "firstname", params = "firstnameStartsWith", spec = StartingWith.class),
    //         @Spec(path = "firstname", params = "firstnameExact", spec = Equal.class),
    //         @Spec(path = "lastname", params = "lastnameContains", spec = Like.class),
    //         @Spec(path = "lastname", params = "lastnameStartsWith", spec = StartingWith.class),
    //         @Spec(path = "lastname", params = "lastnameExact", spec = Equal.class),
    //         // @Spec(path = "email", params = "email", spec = Like.class),
    //         // @Spec(path = "id", params = "id", spec = Equal.class),
    //         // @Spec(path = "id", params = "idGreaterThan", spec = GreaterThan.class),
    //         // @Spec(path = "id", params = "idLessThan", spec = LessThan.class)
    //     }) Specification<User> spec,
    //     Pageable pageable) {
    //     return userRepository.findAll(spec, pageable);
    // }
    // @GetMapping("/users")
    // public ResponseEntity<List<User>> getAllUsers() {
    // List<User> users = userService.getAllUsers();
    // return new ResponseEntity<>(users, HttpStatus.OK);
    // }

    // @GetMapping("/users")
    // public ResponseEntity<Page<User>> getAllUsers(
    // @RequestParam(required = false) String firstname_contains,
    // @RequestParam(required = false) String firstname_startsWith,
    // @RequestParam(required = false) String firstname_equals,

    // Pageable pageable) {
    // Page<User> users = userService.getAllUsers(pageable);
    // return new ResponseEntity<>(users, HttpStatus.OK);
    // }


    // @GetMapping(value = "/users")
    // public Page<User> getAllUsers(
    //         @RequestParam(required = false) String firstname,
    //         @RequestParam(required = false) String role,
    //         Pageable pageable) {
    //     Specification<User> spec = Specification.where(null);
    //     if (firstname != null) {
    //         spec = spec.and(UserSpecifications.hasFirstname(firstname));
    //     }
    //     if (role != null) {
    //         spec = spec.and(UserSpecifications.hasRole(role));
    //     }
    //     return userRepository.findAll(spec, pageable);
    // }




    // @GetMapping(value = "/users")
    // Page<?> search(@Filter Specification<User> spec, Pageable page) {
    // return userRepository(spec, page);
    // }
    // @GetMapping(value = "/users")
    // public Page<User> search(Specification<User> spec, Pageable pageable) {
    // return userRepository.findAll(spec, pageable);
    // }

}
