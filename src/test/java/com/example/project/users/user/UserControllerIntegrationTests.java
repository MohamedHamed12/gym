// package com.example.project.users.user;

// import com.example.project.users.controller.UserController;
// import com.example.project.users.dto.LoginRequest;
// import com.example.project.users.dto.RegisterRequest;
// import com.example.project.users.entity.User;
// import com.example.project.users.enums.Role;
// import com.example.project.users.repository.UserRepository;
// import com.example.project.users.service.*;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import static org.mockito.Mockito.*;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

// // @WebMvcTest(UserController.class)

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
// @ActiveProfiles("test")

// public class UserControllerIntegrationTests {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private JwtService jwtService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private String token;
//     private User user;

//     @BeforeEach
//     void setup() {
//         // Initialize mocks or setup state before each test if necessary
//         user = new User();
//         user.setFirstname("John");
//         user.setLastname("Doe");
//         user.setEmail("johndoe@example.com");
//         user.setPassword("password"); // Make sure to encode the password if needed
//         user.setEmailConfirmed(true); // Assuming email confirmation is required
//         user.setRole(Role.User);
//         userRepository.save(user);

//         // Generate a token for the created user
//         token = jwtService.generateToken(user); 
//     }

//     @Test
//     // @WithMockUser(username  = "user", roles = { "USER" }) // Ensure the user has the necessary role
//     public void testDeleteUser() throws Exception {

//         Long userId = user.getId();

//         mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
//                     // .with(csrf()) 
//                                     .header("Authorization", "Bearer " + token)

//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(MockMvcResultMatchers.status().isNoContent())
//                 .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));

//         // Verify the service method was called
//         verify(userService, times(1)).deleteUser(userId);
//     }

// }
package com.example.project.users.user;

import com.example.project.users.dto.LoginRequest;
import com.example.project.users.dto.RegisterRequest;
import com.example.project.users.entity.User;
import com.example.project.users.enums.Role;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.JwtService;
import com.example.project.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password"); // Make sure to encode the password if needed
        user.setEmailConfirmed(true); // Assuming email confirmation is required
        user.setRole(Role.User);
        userRepository.save(user);

        token = jwtService.generateToken(user);
    }

    // @Test
    // @DirtiesContext

    // public void testRegisterUser() throws Exception {
    //     RegisterRequest registerRequest = new RegisterRequest();
    //     registerRequest.setFirstname("Jane");
    //     registerRequest.setLastname("Doe");
    //     registerRequest.setEmail("janedoe@example.com");
    //     registerRequest.setPassword("password");

    //     mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(registerRequest)))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             // .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));
    // }

    // @Test
    // @DirtiesContext
    // public void testLoginUser() throws Exception {
    //     LoginRequest loginRequest = new LoginRequest();
    //     loginRequest.setEmail("johndoe@example.com");
    //     loginRequest.setPassword("password");

    //     mockMvc.perform(MockMvcRequestBuilders.post("/account/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(loginRequest)))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    // }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));

        // verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        Long invalidUserId = 999L;

        // doThrow(new RuntimeException("User not found")).when(userService).deleteUser(invalidUserId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", invalidUserId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));

        // verify(userService, times(1)).deleteUser(invalidUserId);
    }

    // @Test
    // public void testUnauthorizedDeleteUser() throws Exception {
    // Long userId = user.getId();

    // mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(MockMvcResultMatchers.status().isUnauthorized())
    // .andExpect(MockMvcResultMatchers.content().string("Unauthorized"));
    // }

    // @Test
    // public void testDeleteUserWithInvalidToken() throws Exception {
    // Long userId = user.getId();

    // mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
    // .header("Authorization", "Bearer " + "invalidToken")
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(MockMvcResultMatchers.status().isUnauthorized())
    // .andExpect(MockMvcResultMatchers.content().string("Unauthorized"));
    // }

    // @Test
    // public void testDeleteUserWithoutToken() throws Exception {
    // Long userId = user.getId();

    // mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(MockMvcResultMatchers.status().isUnauthorized())
    // .andExpect(MockMvcResultMatchers.content().string("Unauthorized"));
    // }
}
