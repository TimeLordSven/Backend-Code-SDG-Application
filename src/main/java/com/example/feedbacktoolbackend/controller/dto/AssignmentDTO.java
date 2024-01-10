package com.example.feedbacktoolbackend.controller.dto;

import com.example.feedbacktoolbackend.util.constraints.DateFormatConstraint;
import com.example.feedbacktoolbackend.util.constraints.FutureConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AssignmentDTO(Integer id,
                            @NotNull(message = "Title can't be empty")
                            @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
                            String title,
                            @NotNull
                            String description,
                            @NotNull
                            String teacherCheatSheet,
                            @FutureConstraint
                            @DateFormatConstraint
                            String deadline) {

}

