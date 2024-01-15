package com.example.feedbacktoolbackend.serviceTests;
/**
 * @author Sven Molenaar
 */
import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.service.AssignmentService;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.AssignmentBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class AssignmentServiceTest {
    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @Test
    public void succesfullReturnAssignmentTitleTest() {
        RegistrationDTO registrationUser = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsing1@hva.nl", "Password1!", "Password1!");
        UserBusiness validTeacher = userService.createUser(registrationUser);

        AssignmentDTO dto = new AssignmentDTO(null, "Good Example Title", "Good Exampledescription", "Good Example cheat sheet", "08-06-2025");

        Integer assignmentId = assignmentService.createAssignment(dto, validTeacher);

        assertNotNull(assignmentId);
    }

    @Test
    public void succesfullReturnAssignmentIdTest() {
        RegistrationDTO registrationUser = new RegistrationDTO("Abraham", "Van", "Helsing", "VanHelsin1g@hva.nl", "Password1!", "Password1!");
        UserBusiness validTeacher = userService.createUser(registrationUser);

        AssignmentDTO dto = new AssignmentDTO(null, "Good Example Title", "Good Example description", "Good Example cheat sheet", "05-08-2024");

        Integer assignmentId = assignmentService.createAssignment(dto, validTeacher);
        assertNotNull(assignmentId);

        AssignmentBusiness assignment = assignmentService.getAssignment(assignmentId);
        assertNotNull(assignment);
        assertEquals("Good Example Title", assignment.getTitle());
    }
}
