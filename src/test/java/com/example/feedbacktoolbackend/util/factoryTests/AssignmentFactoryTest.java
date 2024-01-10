package com.example.feedbacktoolbackend.util.factoryTests;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.data.models.Assignment;
import com.example.feedbacktoolbackend.data.models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.AssignmentBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.factory.AssignmentFactory;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AssignmentFactoryTest {

    @Mock
    private UserFactory userFactory;

    private AssignmentFactory assignmentFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assignmentFactory = new AssignmentFactory(userFactory);
    }

    @Test
    void convertToDataEntity() {
        UserBusiness userBusiness = new UserBusiness(1L, "Abraham", "Van", "Helsing", "VanHelsing@hva.nl", "Password1!", Role.TEACHER);
        AssignmentBusiness assignmentBusiness = new AssignmentBusiness(
                1, "Title", "Description", "CheatSheet", LocalDate.parse("2024-01-02"), userBusiness);

        when(userFactory.convertToDataEntity(userBusiness)).thenReturn(new User());

        Assignment result = assignmentFactory.convertToDataEntity(assignmentBusiness);

        assertEquals(assignmentBusiness.getTitle(), result.getTitle());
        assertEquals(assignmentBusiness.getDescription(), result.getDescription());
        assertEquals(assignmentBusiness.getTeacherCheatSheet(), result.getTeacherCheatSheet());
        assertEquals(assignmentBusiness.getDeadline(), result.getDeadline());

    }

    @Test
    void createBusinessModelFromDTOAndUserBusiness() {
        User userEntity = new User();
        UserBusiness teacher = new UserBusiness(1L, "Abraham", "Van", "Helsing", "VanHelsing@hva.nl", "Password1!", Role.TEACHER);
        AssignmentDTO assignmentDTO = new AssignmentDTO(1, "Title", "Description", "CheatSheet", "01-02-2024");

        when(userFactory.createBusinessModel(userEntity)).thenReturn(teacher);

        AssignmentBusiness result = assignmentFactory.createBusinessModel(assignmentDTO, teacher);

        assertEquals(assignmentDTO.title(), result.getTitle());
        assertEquals(assignmentDTO.description(), result.getDescription());
        assertEquals(assignmentDTO.teacherCheatSheet(), result.getTeacherCheatSheet());
        assertEquals(teacher, result.getCreatedBy());
    }

}
