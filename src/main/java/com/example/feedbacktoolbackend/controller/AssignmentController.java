package com.example.feedbacktoolbackend.controller;
/**
 * @author Sven Molenaar
 */
import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.controller.exception.AuthorisationException;
import com.example.feedbacktoolbackend.service.AssignmentService;
import com.example.feedbacktoolbackend.service.models.AssignmentBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.annotations.AuthorizedTeacher;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller handling HTTP requests related to assignments.
 * Manages endpoints for fetching and creating assignments.
 * Handles input validation and authorization.
 *
 * @author Sven Molenaar
 */
@RestController
@RequestMapping("/assignments")
@Validated
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * Endpoint to retrieve an assignment by ID.
     *
     * @return AssignmentDTO representing the retrieved assignment
     * @author Sven Molenaar
     */
    @GetMapping
    public AssignmentDTO getAssignment() {
        AssignmentBusiness assignment = assignmentService.getAssignment(1);
        return convertToDTO(assignment);
    }

    /**
     * Endpoint to create a new assignment.
     *
     * @param assignmentDTO The AssignmentDTO object containing assignment data
     * @param teacher       The UserBusiness object representing the teacher
     * @return ResponseEntity with a success message and the created assignment's ID, or an error message and status code
     * @author Sven Molenaar
     */
    @PostMapping
    public ResponseEntity<Map<String, ?>> createAssignment(@RequestBody @Valid AssignmentDTO assignmentDTO, @AuthorizedTeacher UserBusiness teacher) {
        try {
            Integer assignmentId = assignmentService.createAssignment(assignmentDTO, teacher);
            return new ResponseEntity<>(Map.of("message", "Successfully created", "assignmentId", assignmentId), HttpStatus.CREATED);
        } catch (AuthorisationException authorizationException) {
            return new ResponseEntity<>(Map.of("message", "Invalid Session"), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Converts an AssignmentBusiness object to an AssignmentDTO object.
     *
     * @param assignmentBusiness The AssignmentBusiness object to convert
     * @return AssignmentDTO representing the assignment data
     * @author Sven Molenaar
     */
    private AssignmentDTO convertToDTO(AssignmentBusiness assignmentBusiness) {
        return new AssignmentDTO(
                assignmentBusiness.getId(),
                assignmentBusiness.getTitle(),
                assignmentBusiness.getDescription(),
                assignmentBusiness.getTeacherCheatSheet(),
                assignmentBusiness.getDeadline().toString()
        );
    }
}
