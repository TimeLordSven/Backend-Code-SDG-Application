package com.example.feedbacktoolbackend.controllerTests;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.service.SessionService;
import com.example.feedbacktoolbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
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
    public class AssignmentControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserService userService;

        @Autowired
        private SessionService sessionService;

        private final String VALID_PASSWORD = "TestPassword1!*";
        private final String VALID_EMAIL = "VanHelsingTestAssignment@hva.nl";


        @BeforeEach
        void setUp() {
            RegistrationDTO validUser = new RegistrationDTO("Bob", "", "Hofman", VALID_EMAIL, VALID_PASSWORD, VALID_PASSWORD);
            userService.createUser(validUser);
        }

        @Test
        @Transactional
        @Rollback
        void testIfTooSmallTitleAndValidSessionIdReturns400Error() throws Exception {
            String invalidTitle = "a";
            AssignmentDTO dto = new AssignmentDTO(null, invalidTitle, "Valid description", "Valid Cheat sheet", "12-12-2024");
            LoginDTO validAuth = new LoginDTO(VALID_EMAIL, VALID_PASSWORD);
            String sessionId = sessionService.login(validAuth);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/assignments")
                            .content(objectMapper.writeValueAsString(dto))
                            .cookie(new Cookie("session_id", sessionId))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Title must be between 2 and 255 characters"));
        }

        @Test
        @Transactional
        @Rollback
        void testIfTooBigTitleAndValidSessionIdReturns400Error() throws Exception {
            int maxStringLength = 256;
            String invalidTitle = "a".repeat(maxStringLength);
            AssignmentDTO dto = new AssignmentDTO(null, invalidTitle, "Valid description", "Valid Cheat sheet", "12-12-2024");
            LoginDTO validAuth = new LoginDTO(VALID_EMAIL, VALID_PASSWORD);
            String sessionId = sessionService.login(validAuth);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/assignments")
                            .content(objectMapper.writeValueAsString(dto))
                            .cookie(new Cookie("session_id", sessionId))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Title must be between 2 and 255 characters"));
        }

        @Test
        @Transactional
        @Rollback
        void testIfEmptyTitleAndValidSessionIdReturns400Error() throws Exception {
            String invalidTitle = null;
            AssignmentDTO dto = new AssignmentDTO(null, null, "Valid description", "Valid Cheat sheet", "12-12-2024");
            LoginDTO validAuth = new LoginDTO(VALID_EMAIL, VALID_PASSWORD);
            String sessionId = sessionService.login(validAuth);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/assignments")
                            .content(objectMapper.writeValueAsString(dto))
                            .cookie(new Cookie("session_id", sessionId))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Title can't be empty"));
        }

        @Test
        @Transactional
        @Rollback
        void testIfValidTitleAndInvalidSessionIdReturns401Error() throws Exception {
            AssignmentDTO dto = new AssignmentDTO(null, "Valid Title", "Valid description", "Valid Cheat sheet", "12-12-2024");
            LoginDTO validAuth = new LoginDTO(VALID_EMAIL, VALID_PASSWORD);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/assignments")
                            .content(objectMapper.writeValueAsString(dto))
                            .cookie(new Cookie("session_id", ""))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Cookies"));
        }
    }

