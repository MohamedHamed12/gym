package com.example.project.users.password;

import com.example.project.users.dto.PasswordResetNewPassword;
import com.example.project.users.dto.PasswordResetOtp;
import com.example.project.users.dto.PasswordResetRequest;

import com.example.project.users.service.AuthenticationService;
import com.example.project.users.service.OtpService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private OtpService otpService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPasswordResetRequestSuccess() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest("test@example.com");

        // Mock the behavior of AuthenticationService to simulate OTP generation
        // doNothing().when(authenticationService).passwordResetRequest(any(PasswordResetRequest.class));
        doNothing().when(authenticationService).passwordResetRequest(any(PasswordResetRequest.class));

        // Perform POST request
        mockMvc.perform(post("/account/reset-request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP sent to email"));
    } 



    @Test
    public void testConfirmResetPasswordSuccess() throws Exception {
        PasswordResetOtp request = new PasswordResetOtp();
        request.setOtp("123456");

        String resetToken = "mockToken";
        when(authenticationService.confirmResetPassword(any(PasswordResetOtp.class))).thenReturn(resetToken);

        // Perform POST request
        mockMvc.perform(post("/account/confirm-reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(resetToken));
    }

    @Test
    public void testConfirmResetPasswordFailure() throws Exception {
        PasswordResetOtp request = new PasswordResetOtp();
        request.setOtp("123456");

        when(authenticationService.confirmResetPassword(any(PasswordResetOtp.class)))
                .thenThrow(new IllegalArgumentException("Invalid OTP"));

        // Perform POST request
        mockMvc.perform(post("/account/confirm-reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Password reset failed: Invalid OTP"));
    }



    @Test
    public void testResetPasseord() throws Exception {
        PasswordResetNewPassword request = new PasswordResetNewPassword();
        request.setNewPassword("newpassword");
        request.setTemporaryToken("toekn");

        doNothing().when(authenticationService).resetPassword(any(PasswordResetNewPassword.class));
                

        // Perform POST request
        mockMvc.perform(post("/account/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    
    
    
}
