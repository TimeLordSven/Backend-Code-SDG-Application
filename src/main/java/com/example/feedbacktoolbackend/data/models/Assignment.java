package com.example.feedbacktoolbackend.data.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * Assignment table in the Database
 * @author Sven Molenaar
 */
@Entity
@Table(name = "assignments")
@EntityListeners(AuditingEntityListener.class)
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String teacherCheatSheet;
    @CreatedDate
    private LocalDate createdAt;
    private LocalDate deadline;
    @ManyToOne
    private User createdBy;

    public Assignment() {
    }

    public Assignment(String title, String description, String teacherCheatSheet, LocalDate deadline, User createdBy) {
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}