package com.example.feedbacktoolbackend.util.factoryTests;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.PasswordEncodingService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserFactoryTest {

    private UserFactory userFactory;

    @Mock
    private PasswordEncodingService passwordEncoderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userFactory = new UserFactory(passwordEncoderService);
    }

    @Test
    void convertToBusinessModelTest() {
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setFirstName("Abraham");
        userEntity.setLastName("Helsing");
        userEntity.setEmail("VanHelsing@Hva.nl");
        userEntity.setPassword("password");
        userEntity.setRole(Role.STUDENT);

        UserBusiness userBusiness = userFactory.convertToBusinessModel(userEntity);

        assertEquals(userEntity.getId(), userBusiness.getId());
        assertEquals(userEntity.getFirstName(), userBusiness.getFirstName());
        assertEquals(userEntity.getLastName(), userBusiness.getLastName());
        assertEquals(userEntity.getEmail(), userBusiness.getEmail());
        assertEquals(userEntity.getPassword(), userBusiness.getPassword());
        assertEquals(userEntity.getRole(), userBusiness.getRole());
    }

    @Test
    void convertToDataEntityTest() {
        UserBusiness userBusiness = new UserBusiness(1L, "Abraham", "Van", "Helsing", "VanHelsing@Hva.nl", "password", Role.STUDENT);

        User userEntity = userFactory.convertToDataEntity(userBusiness);

        assertEquals(userBusiness.getId(), userEntity.getId());
        assertEquals(userBusiness.getFirstName(), userEntity.getFirstName());
        assertEquals(userBusiness.getLastName(), userEntity.getLastName());
        assertEquals(userBusiness.getEmail(), userEntity.getEmail());
        assertEquals(userBusiness.getPassword(), userEntity.getPassword());
        assertEquals(userBusiness.getRole(), userEntity.getRole());
    }

    @Test
    void createUserBusinessFromDTOTest() throws Exception {
        when(passwordEncoderService.encodePassword(anyString())).thenReturn("encodedPassword");

        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham", "Van", "Helsing", "VanHelsing@Hva.nl", "password", "password");

        UserBusiness userBusiness = userFactory.createUserBusinessFromDTO(registrationDTO);

        assertEquals(registrationDTO.firstName(), userBusiness.getFirstName());
        assertEquals(registrationDTO.lastName(), userBusiness.getLastName());
        assertEquals(registrationDTO.email(), userBusiness.getEmail());
        assertEquals("encodedPassword", userBusiness.getPassword());
        assertEquals(Role.STUDENT, userBusiness.getRole());
    }
}
