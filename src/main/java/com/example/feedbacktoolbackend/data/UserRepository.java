package com.example.feedbacktoolbackend.data;

import com.example.feedbacktoolbackend.data.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for User entities, extending JpaRepository.
 * This interface provides CRUD operations and query functionality for User entities.
 * @author Sven Molenaar
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
