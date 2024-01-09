package com.example.feedbacktoolbackend.data;

import com.example.feedbacktoolbackend.data.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
}
