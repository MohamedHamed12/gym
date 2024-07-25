package com.example.project.vaildation;


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
public class VaildationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

//     // @Test
//     // @DirtiesContext
//     // public void testRegister() throws Exception {
//     //     RegisterRequest registerRequest = new RegisterRequest();
//     //     registerRequest.setFirstname("John");
//     //     registerRequest.setLastname("Doe");
//     //     registerRequest.setEmail("johndoe1@example.com");
//     //     registerRequest.setPassword("password123");

//     //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(registerRequest)))
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.firstname").value("John"))
//     //             .andExpect(jsonPath("$.lastname").value("Doe"))
//     //             .andExpect(jsonPath("$.email").value("johndoe1@example.com"));

//     //     User user = userRepository.findByEmail("johndoe1@example.com").orElse(null);
//     //     assertNotNull(user);
//     //     assertEquals("John", user.getFirstname());
//     //     assertEquals("Doe", user.getLastname());
//     //     assertEquals("johndoe1@example.com", user.getEmail());
//     // }

    @Test
    @DirtiesContext
    public void testRegisterWithInvalidEmail() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("invalid-email");
        registerRequest.setPassword("password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }

//     // @Test
//     // @DirtiesContext
//     // public void testRegisterWithMissingFirstName() throws Exception {
//     //     RegisterRequest registerRequest = new RegisterRequest();
//     //     registerRequest.setFirstname("");
//     //     registerRequest.setLastname("Doe");
//     //     registerRequest.setEmail("johndoe1@example.com");
//     //     registerRequest.setPassword("password123");

//     //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(registerRequest)))
//     //             .andExpect(status().isBadRequest())
//     //             .andExpect(jsonPath("$.firstname").value("First name is mandatory"));
//     // }

//     // @Test
//     // @DirtiesContext
//     // public void testRegisterDuplicateEmail() throws Exception {
//     //     RegisterRequest registerRequest = new RegisterRequest();
//     //     registerRequest.setFirstname("John");
//     //     registerRequest.setLastname("Doe");
//     //     registerRequest.setEmail("johndoe1@example.com");
//     //     registerRequest.setPassword("password123");

//     //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(registerRequest)))
//     //             .andExpect(status().isOk());

//     //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(registerRequest)))
//     //             .andExpect(status().isConflict());
//     // }

//     // @Test
//     // @Transactional
//     // public void testRegisterNoData() throws Exception {
//     //     ResultHandler printStatus = new ResultHandler() {
//     //         @Override
//     //         public void handle(MvcResult result) throws Exception {
//     //             int status = result.getResponse().getStatus();
//     //             System.out.println("Response Status: " + status);
//     //         }
//     //     };

//     //     RegisterRequest registerRequest = new RegisterRequest();

//     //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(registerRequest)))
//     //             .andDo(printStatus)
//     //             .andExpect(status().isBadRequest());
//     // }
}
