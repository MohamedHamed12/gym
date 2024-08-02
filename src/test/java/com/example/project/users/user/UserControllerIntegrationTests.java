package com.example.project.users.user;




import com.example.project.users.controller.UserController;
import com.example.project.users.entity.User;
import com.example.project.users.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        // Initialize mocks or setup state before each test if necessary
    }

    // @Test
    // public void testDeleteUser() throws Exception {
    //     Long userId = 1L;

    //     // Configure the mock service to return nothing when deleting a user
    //     doNothing().when(userService).deleteUser(userId);

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(MockMvcResultMatchers.status().isNoContent())
    //             .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));

    //     // Verify the service method was called
    //     verify(userService, times(1)).deleteUser(userId);
    // }

    // @Test
    // public void testDeleteUserNotFound() throws Exception {
    //     Long userId = 1L;

    //     // Configure the mock service to throw an exception when the user is not found
    //     doThrow(new RuntimeException("User not found")).when(userService).deleteUser(userId);

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/account/users/{id}", userId)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(MockMvcResultMatchers.status().isNotFound())
    //             .andExpect(MockMvcResultMatchers.content().string("User not found"));

    //     // Verify the service method was called
    //     verify(userService, times(1)).deleteUser(userId);
    // }

    // @Test
    // public void testDeleteAllUsers() throws Exception {
    //     // Configure the mock service to return nothing when deleting all users
    //     doNothing().when(userService).deleteAllUsers();

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/account/users")
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(MockMvcResultMatchers.status().isNoContent())
    //             .andExpect(MockMvcResultMatchers.content().string("All users deleted successfully"));

    //     // Verify the service method was called
    //     verify(userService, times(1)).deleteAllUsers();
    // }

    // @Test
    // public void testDeleteAllUsersError() throws Exception {
    //     // Configure the mock service to throw an exception when deleting all users
    //     doThrow(new RuntimeException("Error deleting users")).when(userService).deleteAllUsers();

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/account/users")
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(MockMvcResultMatchers.status().isInternalServerError())
    //             .andExpect(MockMvcResultMatchers.content().string("Error deleting users"));

    //     // Verify the service method was called
    //     verify(userService, times(1)).deleteAllUsers();
    // }
}
