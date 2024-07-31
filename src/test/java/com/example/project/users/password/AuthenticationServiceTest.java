package com.example.project.users.password;


import com.example.project.users.dto.PasswordChangeRequest;
import com.example.project.users.dto.PasswordResetNewPassword;
import com.example.project.users.dto.PasswordResetOtp;
import com.example.project.users.dto.PasswordResetRequest;
import com.example.project.users.entity.Otp;
import com.example.project.users.entity.PasswordResetToken;
import com.example.project.users.entity.User;
import com.example.project.users.repository.OtpRepository;
import com.example.project.users.repository.PasswordResetTokenRepository;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.AuthenticationService;
import com.example.project.users.service.JwtService;
import com.example.project.users.service.OtpService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private OtpService otpService;


    @Mock
    private JwtService jwtService;
    
   @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPasswordResetRequest() {
        PasswordResetRequest request = new PasswordResetRequest("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        doNothing().when(otpService).generateAndSendOtp(any(String.class));

        authenticationService.passwordResetRequest(request);

        verify(otpService).generateAndSendOtp("test@example.com");
    }



    @Test
    void testConfirmResetPassword() {
        PasswordResetOtp request = new PasswordResetOtp( "123456");
            
        User user = new User();
            user.setEmail("test1@example.com");
            user.setPassword("newPassword");

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtp("123456");
        otp.setExpirationTime(Instant.now().plus(10, ChronoUnit.MINUTES).toEpochMilli());
  
        
            // when(jwtService.generateTemporaryToken(any(String.class)))
                // .thenReturn("generatedToken");
        when(otpService.verifyOtp(anyString())).thenReturn(true);
        
        when(otpRepository.findByOtp(any(String.class)))
                .thenReturn(Optional.of(otp));


        // doNothing().when(userRepository).save(any(User.class));
        when(userRepository.save(any(User.class))).thenReturn(user);
        authenticationService.confirmResetPassword(request);
        
        verify(otpService).verifyOtp(anyString());
        verify(otpRepository).delete(any(Otp.class));
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));

        // verify(userRepository).save(any(User.class));
    }


    @Test
    void testPasswordReset() {

        User user = new User();
        user.setEmail("test2@example.com");
        user.setPassword("Password");



        PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(user);
		passwordResetToken.setToken("token");
		passwordResetTokenRepository.save(passwordResetToken);


       
        PasswordResetNewPassword request = new PasswordResetNewPassword();
        request.setNewPassword("newpassword");
        request.setTemporaryToken("token");

       	// PasswordResetToken passwordReset =passwordResetTokenRepository.findByToken(temporaryToken)
		// 		.orElseThrow(() -> new IllegalArgumentException("Token not found"));
        // when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(passwordResetToken);

        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(Optional.of(passwordResetToken));




        authenticationService.resetPassword(request);
        

        verify(userRepository).save(any(User.class));
    }
}
