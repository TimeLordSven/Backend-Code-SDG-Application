package com.example.feedbacktoolbackend.controllerTests;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        RegistrationDTO validUser = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestSession@hva.nl", "Password1!*", "Password1!*");
        userService.createUser(validUser);
    }

    @Test
    @Transactional
    @Rollback
    void testIfCorrectCredentialsCanLogin() throws Exception {
        LoginDTO dto = new LoginDTO("VanHelsingTest@hva.nl", "Password1!*");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sessions")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("session_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successful login"));
    }

    @Test
    @Transactional
    @Rollback
    void testIfIncorrectEmailCanNotLogin() throws Exception {
        LoginDTO dto = new LoginDTO("WrongVanHelsingTest@hva", "Password1!*");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sessions")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("session_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The email and password do not match"));
    }

    @Test
    @Transactional
    @Rollback
    void testIfEmptyEmailCanNotLogin() throws Exception {
        LoginDTO dto = new LoginDTO("", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sessions")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("session_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The email and password do not match"));
    }

    @Test
    @Transactional
    @Rollback
    void testIfNonExistingEmailCanNotLogin() throws Exception {
        LoginDTO dto = new LoginDTO("nonExisting@example.com", "Password1!*");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sessions")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("session_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("The email and password do not match"));
    }
}