package com.example.project.users.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.users.dto.GeneralResponse;
import com.example.project.users.dto.UserDTO;
import com.example.project.users.dto.UserFilterDTO;
import com.example.project.users.entity.User;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.specs.UserSpecification;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // return userRepository.findByEmail(username)
                // .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

            }

        };

    }

    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User not found");

        }
    }

    public User saveUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmailConfirmed(userDTO.getEmailConfirmed());
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole());
            user.setEmailConfirmed(userDTO.getEmailConfirmed());
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User not found");
        }
    }

    public Page<User> findAllUsers(UserFilterDTO filterDTO, Pageable pageable) {
        final Specification<User> specification = UserSpecification.filterUser(filterDTO);
        return userRepository.findAll(specification, pageable);
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public boolean isAuthorizedToDeleteUser(String username, Long userId) {
        // Fetch user from repository by userId
        System.out.println("***********************is auth****" + username);
        System.out.println(userId);

        User user = userRepository.findById(userId).orElse(null);

        // Check if the user exists and if the username matches
        return user != null && user.getUsername().equals(username);
    }
}
