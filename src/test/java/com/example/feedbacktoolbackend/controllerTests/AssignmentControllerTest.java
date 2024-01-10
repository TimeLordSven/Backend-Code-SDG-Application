package com.example.feedbacktoolbackend.controllerTests;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.AssignmentController;
import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.controller.exception.AuthorisationException;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.AssignmentService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssignmentControllerTest {
    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateAssignment_Successful() {
        AssignmentDTO assignmentDTO = new AssignmentDTO(1, "Assignment", "Description", "CheatSheet", "2024-01-10");
        UserBusiness teacher = new UserBusiness(1L, "Abraham", "Van", "Helsing", "VanHelsing@hva.nl", "Password1!", Role.TEACHER);

        when(assignmentService.createAssignment(any(AssignmentDTO.class), any(UserBusiness.class))).thenReturn(1);

        ResponseEntity<Map<String, ?>> response = assignmentController.createAssignment(assignmentDTO, teacher);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Map.of("message", "Successfully created", "assignmentId", 1), response.getBody());
        verify(assignmentService, times(1)).createAssignment(assignmentDTO, teacher);
    }

    @Test
    void testCreateAssignment_Unauthorized() {
        AssignmentDTO assignmentDTO = new AssignmentDTO(1, "Assignment", "Description", "CheatSheet", "2024-01-10");
        UserBusiness teacher = new UserBusiness(1L, "Abraham", "Van", "Helsing", "VanHelsing@hva.nl", "Password1!", Role.TEACHER);

        doThrow(new AuthorisationException("Invalid Session")).when(assignmentService).createAssignment(any(AssignmentDTO.class), any(UserBusiness.class));

        ResponseEntity<Map<String, ?>> response = assignmentController.createAssignment(assignmentDTO, teacher);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(Map.of("message", "Invalid Session"), response.getBody());
        verify(assignmentService, times(1)).createAssignment(assignmentDTO, teacher);
    }
}
