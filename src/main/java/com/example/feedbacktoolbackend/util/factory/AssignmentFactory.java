package com.example.feedbacktoolbackend.util.factory;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.AssignmentDTO;
import com.example.feedbacktoolbackend.data.models.Assignment;
import com.example.feedbacktoolbackend.service.models.AssignmentBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Factory class responsible for creating AssignmentBusiness and Assignment entities.
 * Converts between AssignmentBusiness, Assignment, and AssignmentDTO objects.
 * Handles data entity and business model conversions for assignments.
 *
 * @author Sven Molenaar
 */
@Component
public class AssignmentFactory implements ModelFactory<AssignmentBusiness, Assignment> {

    private final UserFactory userFactory;

    public AssignmentFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    /**
     * Converts an AssignmentBusiness object to its data entity representation (Assignment).
     *
     * @param assignmentBusiness The AssignmentBusiness object to convert
     * @return Assignment object representing the data entity
     * @author Sven Molenaar
     */
    public Assignment createDataEntity(AssignmentBusiness assignmentBusiness) {
        return new Assignment(
                assignmentBusiness.getTitle(),
                assignmentBusiness.getDescription(),
                assignmentBusiness.getTeacherCheatSheet(),
                assignmentBusiness.getDeadline(),
                userFactory.createDataEntity(assignmentBusiness.getCreatedBy())
        );
    }

    /**
     * Creates an AssignmentBusiness object from an Assignment data entity.
     *
     * @param assignmentEntity The Assignment entity to create a business model from
     * @return AssignmentBusiness object representing the business model
     * @author Sven Molenaar
     */
    public AssignmentBusiness createBusinessModel(Assignment assignmentEntity) {
        return new AssignmentBusiness(
                assignmentEntity.getId(),
                assignmentEntity.getTitle(),
                assignmentEntity.getDescription(),
                assignmentEntity.getTeacherCheatSheet(),
                assignmentEntity.getDeadline(),
                userFactory.createBusinessModel(assignmentEntity.getCreatedBy())
        );
    }

    /**
     * Creates an AssignmentBusiness object from AssignmentDTO and a UserBusiness object.
     *
     * @param assignmentDTO The AssignmentDTO object containing assignment data
     * @param teacher       The UserBusiness object representing the teacher
     * @return AssignmentBusiness object representing the business model
     * @author Sven Molenaar
     */
    public AssignmentBusiness createBusinessModel(AssignmentDTO assignmentDTO, UserBusiness teacher) {
        return new AssignmentBusiness(
                null,
                assignmentDTO.title(),
                assignmentDTO.description(),
                assignmentDTO.teacherCheatSheet(),
                LocalDate.parse(assignmentDTO.deadline(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                teacher
        );
    }
}
