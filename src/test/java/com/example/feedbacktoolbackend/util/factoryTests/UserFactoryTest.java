package com.example.feedbacktoolbackend.util.factoryTests;
/**
 * Tests for the UserFactory
 * @author Sven Molenaar
 */
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserFactoryTest {

    @Test
    public void testConvertToBusinessModel_PositiveCase() {
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");

        UserFactory userFactory = new UserFactory();

        UserBusiness result = userFactory.convertToBusinessModel(userEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }


    @Test
    public void testConvertToDataEntity_PositiveCase() {
        UserBusiness userBusiness = new UserBusiness(1L, "John", "", "Doe", "john@example.com", "password123", Role.STUDENT);
        UserFactory userFactory = new UserFactory();

        User result = userFactory.convertToDataEntity(userBusiness);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("", result.getPrefixes());
        assertEquals("Doe", result.getLastName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());
        assertEquals(Role.STUDENT, result.getRole());
    }
}
