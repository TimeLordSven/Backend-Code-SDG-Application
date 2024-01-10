package com.example.feedbacktoolbackend.service;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.data.AssignmentRepository;
import com.example.feedbacktoolbackend.service.models.AssignmentBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.factory.AssignmentFactory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service handling operations related to assignments.
 * Handles creating and retrieving assignments.
 *
 * @author Sven Molenaar
 */
@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentFactory assignmentFactory;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentFactory assignmentFactory) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentFactory = assignmentFactory;
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param id The ID of the assignment to retrieve
     * @return AssignmentBusiness object representing the retrieved assignment
     * @author Sven Molenaar
     */
    public AssignmentBusiness getAssignment(Integer id) {
        return assignmentFactory.createBusinessModel(assignmentRepository.getReferenceById(id));
    }

    /**
     * Creates a new assignment associated with a teacher.
     *
     * @param assignmentDTO The AssignmentDTO object containing assignment data
     * @param teacher       The UserBusiness object representing the teacher
     * @return The ID of the created assignment
     * @throws InvalidInputException if the input for assignment creation is invalid
     * @author Sven Molenaar
     */
    public Integer createAssignment(AssignmentDTO assignmentDTO, UserBusiness teacher) throws InvalidInputException {
        AssignmentBusiness assignmentBusiness = assignmentFactory.createBusinessModel(assignmentDTO, teacher);
        assignmentBusiness = saveAssignment(assignmentBusiness);
        return assignmentBusiness.getId();
    }

    /**
     * Saves the assignment to the repository.
     *
     * @param assignmentBusiness The AssignmentBusiness object to save
     * @return AssignmentBusiness object representing the saved assignment
     * @throws InvalidInputException if the input for assignment saving is invalid
     * @author Sven Molenaar
     */
    private AssignmentBusiness saveAssignment(AssignmentBusiness assignmentBusiness) {
        return assignmentFactory.createBusinessModel(assignmentRepository.save(assignmentFactory.convertToDataEntity(assignmentBusiness)));
    }
}
