package com.example.project.users.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.users.Exception.UserAlreadyExistsException;
import com.example.project.users.dto.JwtAuthenticationRequest;
import com.example.project.users.dto.LoginRequest;
import com.example.project.users.dto.PasswordChangeRequest;

import com.example.project.users.dto.PasswordResetNewPassword;
import com.example.project.users.dto.PasswordResetOtp;
import com.example.project.users.dto.PasswordResetRequest;
import com.example.project.users.dto.RefreshTokenRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.entity.Otp;
import com.example.project.users.entity.PasswordResetToken;
// import com.example.project.users.entity.Order;
import com.example.project.users.entity.User;
// import com.example.project.users.enums.OrderStatus;
import com.example.project.users.enums.Role;
import com.example.project.users.repository.OtpRepository;
import com.example.project.users.repository.PasswordResetTokenRepository;
// import com.example.project.users.repository.OrderRepository;
import com.example.project.users.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private OtpRepository otpRepository;

	public User signUp(RegisterRequest signUpRequest) {
		// System.out.println("***********************************************");

		Optional<User> existingUserOptional = userRepository.findByEmail(signUpRequest.getEmail());

		if (existingUserOptional.isPresent()) {
			User existingUser = existingUserOptional.get();

			if (!existingUser.getEmailConfirmed()) {
				// // Delete the user with unconfirmed email
				userRepository.delete(existingUser);

				userRepository.flush();

			}
		}

		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.User);
		userRepository.save(user);

		return user;
	}

	public JwtAuthenticationRequest login(LoginRequest loginRequest) {

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password", e);
		}

		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid user after authenticate"));

		// Check if the email is confirmed
		if (!user.getEmailConfirmed()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Email not confirmed. Please confirm your email to log in.");
		}

		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		JwtAuthenticationRequest jwtAuthResponse = new JwtAuthenticationRequest();
		jwtAuthResponse.setToken(jwt);
		jwtAuthResponse.setRefreshToken(refreshToken);
		jwtAuthResponse.setUser(user);
		return jwtAuthResponse;
	}

	public JwtAuthenticationRequest refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			var jwt = jwtService.generateToken(user);
			JwtAuthenticationRequest jwtAuthResponse = new JwtAuthenticationRequest();
			jwtAuthResponse.setToken(jwt);
			jwtAuthResponse.setRefreshToken(refreshTokenRequest.getToken());
			jwtAuthResponse.setUser(user);
			return jwtAuthResponse;
		} else {
			throw new IllegalArgumentException("Invalid or expired refresh token");
		}
	}

	public User getCurrentUser() {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String currentUserName = authentication.getName();
		return userRepository.findByEmail(currentUserName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
	}

	public void changePassword(User user, PasswordChangeRequest passwordChangeRequest) {
		if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Old password is incorrect");
		}
		user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(user);
	}

	// public void resetPassword(PasswordResetRequest passwordResetRequest) {
	// User user = userRepository.findByEmail(passwordResetRequest.getEmail())
	// .orElseThrow(() -> new IllegalArgumentException("User not found"));
	// otpService.generateAndSendOtp(user.getEmail());
	// }

	public void resetPassword(PasswordResetNewPassword passwordresetnewpassword) {
		String temporaryToken = passwordresetnewpassword.getTemporaryToken();

		PasswordResetToken passwordReset = passwordResetTokenRepository.findByToken(temporaryToken)
				.orElseThrow(() -> new IllegalArgumentException("Token not found"));
		User user = passwordReset.getUser();
		user.setPassword(passwordresetnewpassword.getNewPassword()); // Ensure you hash the password appropriately
		userRepository.save(user);
	}

	public String confirmResetPassword(PasswordResetOtp passwordResetOtp) {

		if (!otpService.verifyOtp(passwordResetOtp.getOtp())) {
			throw new IllegalArgumentException("Invalid OTP");
		}
		Otp otpEntity = otpRepository.findByOtp(passwordResetOtp.getOtp())
				.orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));

		String resetToken = UUID.randomUUID().toString();

		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(otpEntity.getUser());
		passwordResetToken.setToken(resetToken);
		passwordResetTokenRepository.save(passwordResetToken);

		otpRepository.delete(otpEntity);

		return resetToken;

	}

	public void passwordResetRequest(PasswordResetRequest passwordResetRequest) {
		// PasswordResetToken tokenEntity
		// =PasswordResetTokenRepository.findByToken(passwordResetRequest.get);
		User user = userRepository.findByEmail(passwordResetRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		otpService.generateAndSendOtp(passwordResetRequest.getEmail());
	}

}
