package com.example.project.users.controller;

import com.example.project.users.controller.OtpController;
import com.example.project.users.dto.OtpRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.entity.User;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.OtpService;
import com.example.project.users.service.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;






@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OtpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OtpService otpService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailSenderService emailSenderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testRegisterSuccess() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        // Mock the behavior of OTP service to just simulate OTP generation
        doNothing().when(otpService).generateAndSendOtp(registerRequest.getEmail());

        // Perform POST request
        mockMvc.perform(post("/all/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }



    @Test
    @Transactional
     public void testVerifyOtpSuccess() throws Exception {


        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        // Mock the behavior of OTP service to just simulate OTP generation
        doNothing().when(otpService).generateAndSendOtp(registerRequest.getEmail());

        // Perform POST request
        mockMvc.perform(post("/all/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    


        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setEmail("johndoe@example.com");
        otpRequest.setOtp("123456");

        // Mock the behavior of OtpService to simulate OTP verification success
        when(otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp())).thenReturn(true);

        // Perform POST request
        mockMvc.perform(post("/all/otp/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(otpRequest)))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.success").value(true));
    }
}





// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// @WebMvcTest(UserController.class)
// public class UserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @MockBean
//     private UserService userService;

//     @MockBean
//     private OtpService otpService;

//     @Test
    
// }
