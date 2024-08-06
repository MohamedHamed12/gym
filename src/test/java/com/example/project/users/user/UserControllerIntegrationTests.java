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
import com.example.project.users.dto.UserDTO;
import com.example.project.users.entity.User;
import com.example.project.users.enums.Role;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.JwtService;
import com.example.project.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.test.web.servlet.MvcResult;


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
    // RegisterRequest registerRequest = new RegisterRequest();
    // registerRequest.setFirstname("Jane");
    // registerRequest.setLastname("Doe");
    // registerRequest.setEmail("janedoe@example.com");
    // registerRequest.setPassword("password");

    // mockMvc.perform(MockMvcRequestBuilders.post("/account/register")
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(registerRequest)))
    // .andExpect(MockMvcResultMatchers.status().isOk())
    // // .andExpect(MockMvcResultMatchers.content().string("User registered
    // successfully"));
    // }

    // @Test
    // @DirtiesContext
    // public void testLoginUser() throws Exception {
    // LoginRequest loginRequest = new LoginRequest();
    // loginRequest.setEmail("johndoe@example.com");
    // loginRequest.setPassword("password");

    // mockMvc.perform(MockMvcRequestBuilders.post("/account/login")
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(loginRequest)))
    // .andExpect(MockMvcResultMatchers.status().isOk())
    // .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    // }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
                // .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())

                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    @DirtiesContext
    public void testDeleteUserNotFound() throws Exception {
        Long invalidUserId = 999L;

        // doThrow(new RuntimeException("User not
        // found")).when(userService).deleteUser(invalidUserId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", invalidUserId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                // .andExpect(MockMvcResultMatchers.content().string("User not found"));
                .andExpect(jsonPath("$.message").value("User not found"));
        // verify(userService, times(1)).deleteUser(invalidUserId);
    }

    // @Test
    // @DirtiesContext

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

    @Test
    @DirtiesContext
    public void createUser_ShouldReturnCreatedUser_WhenValidRequest() throws Exception {
        // UserDTO userDTO = new UserDTO("aa", "sss", "fff@gmail.com", "password",
        // false);
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("john.doe2@example.com");
        userDTO.setFirstname("John");
        userDTO.setLastname("Doe");
        userDTO.setPassword("password123");

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe2@example.com"));
    }

    @Test
    @DirtiesContext
    public void createUser_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail("john.doe");
        // userDTO.setFirstname("John");
        userDTO.setLastname("Doe");
        userDTO.setPassword("123");

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.firstname").value("First name is mandatory"))
                .andExpect(jsonPath("$.email").value("Email should be valid"))
                .andExpect(jsonPath("$.password").value("Password should be at least 6 characters long"));
        // .andExpect(jsonPath("$.emailConfirmed").value("Email confirmed status is
        // mandatory"));
    }

    // @Test
    // @DirtiesContext

    // public void updateUser_ShouldReturnUpdatedUser_WhenValidRequest() throws Exception {

    //     UserDTO userDTO = new UserDTO();

    //     userDTO.setEmail("john.doe2@example.com");
    //     userDTO.setFirstname("John2");
    //     userDTO.setLastname("Doe2");
    //     userDTO.setPassword("password123");

    //     User user = new User(1, "Jane", "Doe", "jane.doe@example.com", "newpassword",
    //             Role.User, true, null);
    //    userRepository.save(user);
    //     // Mockito.when(userService.updateUser(Mockito.eq(1L),
    //     // Mockito.any(UserDTO.class))).thenReturn(user);

    //     // mockMvc.perform(put(f"/account/users/{user.getid}")
    //     mockMvc.perform(put("/account/users/{id}", user.getId())

    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(userDTO)))
    //             .andExpect(MockMvcResultMatchers.status().isOk());
    //             // .andExpect(jsonPath("$.id").value(1))
    //             // .andExpect(jsonPath("$.firstname").value("Jane2"))
    //             // .andExpect(jsonPath("$.lastname").value("Doe2"))
    //             // .andExpect(jsonPath("$.email").value("jane.doe2@example.com"));
    // }

    @Test 
    @DirtiesContext

    public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        User user1 = new User(1, "John", "Doe", "john.doe@example.com", "password123", Role.User, false, null);
        User user2 = new User(2, "Jane", "Doe", "jane.doe@example.com", "password123", Role.Admin, true, null);
        List<User> users = Arrays.asList(user1, user2);
       userRepository.save(user1);
              userRepository.save(user2);


        // Mockito.when(userService.getAllUsers()).thenReturn(users);

        MvcResult result=mockMvc.perform(get("/account/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())  
                  .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);


         result=mockMvc.perform(get("/account/users?page=0&size=10"))
                .andExpect(MockMvcResultMatchers.status().isOk())  
                  .andReturn();
            
         content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
                // .andExpect(jsonPath("$[0].id").value(1))
                // .andExpect(jsonPath("$[0].firstname").value("John"))
                // .andExpect(jsonPath("$[0].lastname").value("Doe"))
                // .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                // // .andExpect(jsonPath("$[1].id").value(2))
                // .andExpect(jsonPath("$[1].firstname").value("Jane"))
                // .andExpect(jsonPath("$[1].lastname").value("Doe"))
                // .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));
    }

    // @Test
    // public void updateUser_ShouldReturnNotFound_WhenUserNotFound() throws
    // Exception {
    // UserDTO userDTO = new UserDTO(0, "Jane", "Doe", "jane.doe@example.com",
    // "newpassword", Role.ADMIN, true);

    // Mockito.when(userService.updateUser(Mockito.eq(1L),
    // Mockito.any(UserDTO.class))).thenThrow(new UserNotFoundException("User not
    // found with id: 1"));

    // mockMvc.perform(put("/api/users/1")
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(userDTO)))
    // .andExpect(status().isNotFound())
    // .andExpect(content().string("User not found with id: 1"));
    // }

    // @Test
    // public void deleteUser_ShouldReturnNoContent_WhenUserDeleted() throws
    // Exception {
    // Mockito.doNothing().when(userService).deleteUser(1L);

    // mockMvc.perform(delete("/api/users/1"))
    // .andExpect(status().isNoContent());
    // }

}
