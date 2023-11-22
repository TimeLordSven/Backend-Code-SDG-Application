package com.example.feedbacktoolbackend.data;

import com.example.feedbacktoolbackend.data.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
