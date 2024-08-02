package com.example.project.users.auth;

import com.example.project.users.dto.RefreshTokenRequest;
import com.example.project.users.dto.JwtAuthenticationRequest;
import com.example.project.users.dto.LoginRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.entity.User;
import com.example.project.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;





@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

//     @Test
//     @DirtiesContext
//     public void testRegister() throws Exception {
        // Create test data
//         RegisterRequest registerRequest = new RegisterRequest();
//         registerRequest.setFirstname("John");
//         registerRequest.setLastname("Doe");
//         registerRequest.setEmail("johndoe1@example.com");
//         registerRequest.setPassword("password");

//         // Perform POST request
//         mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(registerRequest)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.firstname").value("John"))
//                 .andExpect(jsonPath("$.lastname").value("Doe"))
//                 .andExpect(jsonPath("$.email").value("johndoe1@example.com"));

//         // Verify the user is saved in repository
//         User user = userRepository.findByEmail("johndoe1@example.com").orElse(null);
//         assertNotNull(user);
//         assertEquals("John", user.getFirstname());
//         assertEquals("Doe", user.getLastname());
//         assertEquals("johndoe1@example.com", user.getEmail());
//     }

//     @Test
//     @DirtiesContext
//     public void testLogin() throws Exception {
//         // Insert test data
//         RegisterRequest registerRequest = new RegisterRequest();
//         registerRequest.setFirstname("John");
//         registerRequest.setLastname("Doe");
//         registerRequest.setEmail("johndoe@example.com");
//         registerRequest.setPassword("password");
//         mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(registerRequest)));

//         User user =userRepository.findByEmail("johndoe@example.com").            
//                 orElseThrow(() -> new IllegalArgumentException("Invalid Email"));
//         user.setEmailConfirmed(true);
//         userRepository.save(user);

        
//         LoginRequest loginRequest = new LoginRequest();
//         loginRequest.setEmail("johndoe@example.com");
//         loginRequest.setPassword("password");
        
//         // Perform POST request
//         mockMvc.perform(MockMvcRequestBuilders.post("/account/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(loginRequest)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.token").exists());
//     }

    
   
    @Test
    @Transactional
    public void testRegisterDuplicateEmail() throws Exception {
 

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("ohndoe1@example.com");
        registerRequest.setPassword("password");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    


        registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("ohndoe1@example.com");
        registerRequest.setPassword("password");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk()) ;// Expect conflict due to duplicate email

    }





//     @Test
//     @Transactional
//     public void testRegisterNoData() throws Exception {
 


//             ResultHandler printStatus = new ResultHandler() {
//             @Override
//             public void handle(MvcResult result) throws Exception {
//                 int status = result.getResponse().getStatus();
//                 System.out.println("Response Status: " + status);
//             }
//         };


//         RegisterRequest registerRequest = new RegisterRequest();

//         System.out.println("************");
      
//         mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(registerRequest)))
//                 .andDo(printStatus)
//                 .andExpect(status().isOk());
    


//     }
}

