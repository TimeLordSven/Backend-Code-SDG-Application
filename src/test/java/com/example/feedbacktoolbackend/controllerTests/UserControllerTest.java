package com.example.feedbacktoolbackend.controllerTests;
/*
  @Author Sven Molenaar
 */
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @Rollback()
    void testCreatesUser() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidFirstNameSpecialCharacter() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham#", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }


    @Test
    @Transactional
    @Rollback()
    void testDuplicateEmail() throws Exception {
        RegistrationDTO dto1 = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");
        RegistrationDTO dto2 = new RegistrationDTO("Adam", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());

        // Attempt to register a user with the same email
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("E-mail is already taken"));
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidFirstNameLength() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("a".repeat(51), "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidFirstNameSpaces() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Ab raham", "Van", "Helsing", "VanHelsingUserTest@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidLastNameSpecialCharacter() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing#", "VanHelsingTestUser@hva.com", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidEmailFormat() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva", "Password1!", "Password1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidPasswordLength() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Pa123!", "Pa123!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidPasswordNoSpecialCharacter() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1", "Password1");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidPasswordSpaces() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Pass word1!", "Pass word1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @Transactional
    @Rollback()
    void testInvalidPasswordMismatch() throws Exception {
        RegistrationDTO dto = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsingTestUser@hva.com", "Password1!", "WrongPassword1!");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }
}

