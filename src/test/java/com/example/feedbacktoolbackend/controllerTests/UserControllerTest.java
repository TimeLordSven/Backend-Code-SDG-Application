
package com.example.feedbacktoolbackend.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Tests for the UserController class, specifically focusing on user creation endpoints.
 *
 * @author Sven Molenaar
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test case to validate the creation of a user through the UserController.
     * Sends a POST request to the endpoint and checks for a successful user creation.
     *
     * @throws Exception if any error occurs during the test
     * @author Sven Molenaar
     */
    @Test
    public void testCreateUserEndpoint() throws Exception {
        String requestJson = "{\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"prefixes\": \"Von\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"email\": \"JohnVonDoe@Hva.com\",\n" +
                "    \"password\": \"Password123!\",\n" +
                "    \"verifyPassword\": \"Password123!\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
