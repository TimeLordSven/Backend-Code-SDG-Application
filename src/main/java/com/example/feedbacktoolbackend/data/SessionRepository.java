package com.example.feedbacktoolbackend.data;

import com.example.feedbacktoolbackend.data.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}