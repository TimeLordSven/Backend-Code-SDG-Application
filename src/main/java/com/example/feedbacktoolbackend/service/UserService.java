package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructor injection for UserService.
     * @author Sven Molenaar
     * @param userRepository The repository for user data.
     */
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user in the business model format by ID.
     * @author Sven Molenaar
     * @param id The ID of the user.
     * @return The user in business model format.
     */
    public UserBusiness getUser(Long id){
        return convertToBusinessModel(userRepository.findById(id));
    }

    /**
     * Converts user data from database entity to a business model object.
     * @author Sven Molenaar
     * @param userData The optional user data retrieved from the database.
     * @return The user data in business model format or null if not found.
     */
    private UserBusiness convertToBusinessModel(Optional<User> userData){
        return userData.map(user -> new UserBusiness(user.id, user.firstName, user.lastName)).orElse(null);
    }
}
