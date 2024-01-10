package com.example.feedbacktoolbackend.service.models;

import java.time.LocalDate;

/**
 * Business model for the Assignment
 *
 * @author Sven Molenaar
 */
public class AssignmentBusiness {
    private Integer id;
    private String title;
    private String description;
    private String teacherCheatSheet;
    private LocalDate deadline;
    private UserBusiness createdBy;

    public AssignmentBusiness(Integer id, String title, String description, String teacherCheatSheet, LocalDate deadline, UserBusiness createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherCheatSheet = teacherCheatSheet;
        this.deadline = deadline;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherCheatSheet() {
        return teacherCheatSheet;
    }

    public void setTeacherCheatSheet(String teacherCheatSheet) {
        this.teacherCheatSheet = teacherCheatSheet;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public UserBusiness getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserBusiness createdBy) {
        this.createdBy = createdBy;
    }

    public boolean hasValidTitle() {
        return this.title != null && !this.title.isBlank();
    }
}
