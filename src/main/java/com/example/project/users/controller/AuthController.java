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
import com.example.project.users.dto.ErrorResponse;
import com.example.project.users.dto.JwtAuthenticationRequest;
import com.example.project.users.dto.LoginRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.dto.SuccessResponse;
import com.example.project.users.entity.User;
import com.example.project.users.service.AuthenticationService;
import com.example.project.users.service.OtpService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/account")
// @CrossOrigin("http://localhost:4200")
public class AuthController {

	@Autowired
	OtpService otpService;

	@Autowired
	AuthenticationService authenticationService;

	@Operation(summary = "Register a new user", description = "Creates a new user account")
	@ApiResponse(responseCode = "200", description = "Successful registration", content = @Content(schema = @Schema(implementation = String.class)))
	@ApiResponse(responseCode = "400", description = "Invalid input")

	// @PreAuthorize("permitAll()")

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
		try {
			User user = authenticationService.signUp(registerRequest);

			otpService.generateAndSendOtp(registerRequest.getEmail());

		
			return ResponseEntity.ok(new SuccessResponse("User registered successfully.otp sent  Please confirm your email."));
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An internal server error occurred. " + e.getMessage()));
		}
	}

	// @PreAuthorize("permitAll()")
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest)
	// throws IllegalAccessException
	{
		// try {

			return ResponseEntity.ok(authenticationService.login(loginRequest));
		// } catch (Exception e) {
		// 	ErrorResponse errorResponse = new ErrorResponse("An internal server error occurred." + e.getMessage());
		// 	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

		// }
	}

	// @PreAuthorize("permitAll()")
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		try{

			return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse("An internal server error occurred." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

		}
	}
}
