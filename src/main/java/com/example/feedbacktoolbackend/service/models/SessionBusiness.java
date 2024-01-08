package com.example.feedbacktoolbackend.service.models;
/**
  @author Sven Molenaar
 */

import java.util.Date;

public class SessionBusiness {
    private String sessionId;
    private UserBusiness user;
    private Date createdAt;


    public SessionBusiness(String sessionId, UserBusiness user, Date createdAt) {
        this.sessionId = sessionId;
        this.user = user;
        this.createdAt = createdAt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserBusiness getUser() {
        return user;
    }

    public void setUser(UserBusiness user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
