package com.example.feedbacktoolbackend.service;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.data.SessionRepository;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.data.Models.Session;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.Converter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
/**
 * Constructor for SessionService.
 *
 * @param passwordEncodingService Password encoder service
 * @param userRepository         User repository
 * @param sessionRepository      Session repository
 * @param converter              Converter utility
 * @author Sven Molenaar
 */
public class SessionService {
    private final PasswordEncodingService passwordEncodingService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Converter converter;

    @Autowired
    public SessionService(PasswordEncodingService passwordEncodingService, UserRepository userRepository,
                          SessionRepository sessionRepository, Converter converter) {
        this.passwordEncodingService = passwordEncodingService;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.converter = converter;
    }

    /**
     * Handles user login functionality.
     *
     * @param loginDTO Login details of the user
     * @return Session ID upon successful login
     * @throws CustomHttpException if login validation fails or an internal server error occurs
     * @author Sven Molenaar
     */
    public String login(LoginDTO loginDTO) {
        try {
            validateAuthenticationDTO(loginDTO);

            UserBusiness user = retrieveUserForAuthentication(loginDTO);
            validatePassword(user, loginDTO.password());

            SessionBusiness sessionBusiness = createSession(user);
            return sessionBusiness.getSessionId();
        } catch (CustomHttpException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    /**
     * Validates the authentication DTO.
     *
     * @param dto Login details
     * @throws CustomHttpException if authentication input is empty
     * @author Sven Molenaar
     */
    private void validateAuthenticationDTO(LoginDTO dto) {
        if (dto.email() == null || dto.email().isBlank() || dto.password() == null || dto.password().isBlank()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Input cannot be empty");
        }
    }

    /**
     * Retrieves user information for authentication.
     *
     * @param loginDTO Login details
     * @return UserBusiness object for authentication
     * @throws CustomHttpException if the email is invalid or user is unauthorized
     * @author Sven Molenaar
     */
    private UserBusiness retrieveUserForAuthentication(LoginDTO loginDTO) {
        UserBusiness user = new UserBusiness(loginDTO.email());
        validateEmail(user);
        return getUserByEmail(user.getEmail());
    }

    /**
     * Validates the email of the user.
     *
     * @param userBusiness UserBusiness object
     * @throws CustomHttpException if the email is invalid
     * @author Sven Molenaar
     */
    private void validateEmail(UserBusiness userBusiness) {
        if (!userBusiness.hasValidEmail()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The Email is not valid");
        }
    }

    /**
     * Retrieves a user by email.
     *
     * @param email User email
     * @return UserBusiness object based on the email
     * @throws CustomHttpException if user is unauthorized
     * @author Sven Molenaar
     */
    private UserBusiness getUserByEmail(String email) {
        User userEntity = userRepository.getUserByEmail(email);
        if (userEntity == null) {
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "The email and password do not match");
        }
        return converter.convertToBusinessModel(userEntity);
    }

    /**
     * Validates the user's password.
     *
     * @param userBusiness UserBusiness object
     * @param rawPassword  Raw password input
     * @throws CustomHttpException if password validation fails
     * @author Sven Molenaar
     */
    private void validatePassword(UserBusiness userBusiness, String rawPassword) {
        passwordEncodingService.validateLoginCredentials(rawPassword, userBusiness.getPassword());
    }

    /**
     * Creates a session for the authenticated user.
     *
     * @param userBusiness UserBusiness object
     * @return SessionBusiness object representing the created session
     * @author Sven Molenaar
     */
    private SessionBusiness createSession(UserBusiness userBusiness) {
        Session session = new Session();
        session.setUser(converter.convertToDataEntity(userBusiness));
        sessionRepository.save(session);
        return converter.convertToBusinessModel(session);
    }
}
