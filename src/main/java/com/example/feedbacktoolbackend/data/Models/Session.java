package com.example.feedbacktoolbackend.data.Models;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@EntityListeners(AuditingEntityListener.class)
public class Session {
    @Id
    @GeneratedValue
    private UUID sessionId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @CreatedDate
    private Date createdAt;

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
