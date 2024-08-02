
package com.example.project.vaildation;

import com.example.project.users.dto.PasswordResetNewPassword;
import com.example.project.users.dto.PasswordResetOtp;
import com.example.project.users.dto.PasswordResetRequest;
import com.example.project.users.dto.RegisterRequest;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VaildationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private OtpService otpService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    public void testRegister() throws Exception {
        // Create test data
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("1johndoe1@example.com");
        registerRequest.setPassword("password");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
        // Verify the user is saved in repository
     
    }
    
    
    
}
