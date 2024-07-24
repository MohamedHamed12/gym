package com.example.project.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.project.users.dto.RefreshTokenRequest;
import com.example.project.users.Exception.UserAlreadyExistsException;
import com.example.project.users.dto.JwtAuthenticationRequest;
import com.example.project.users.dto.LoginRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.entity.User;
import com.example.project.users.service.AuthenticationService;
import com.example.project.users.service.OtpService;

@RestController
@RequestMapping("/account")
// @CrossOrigin("http://localhost:4200")
public class AuthController {

	@Autowired
	OtpService otpService;


	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		// return ResponseEntity.ok(authenticationService.signUp(registerRequest));

		 try {
            User user = authenticationService.signUp(registerRequest);


			otpService.generateAndSendOtp(registerRequest.getEmail());


            return ResponseEntity.ok(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationRequest> login(@RequestBody LoginRequest loginRequest) throws IllegalAccessException{
		return ResponseEntity.ok(authenticationService.login(loginRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationRequest> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}
}
