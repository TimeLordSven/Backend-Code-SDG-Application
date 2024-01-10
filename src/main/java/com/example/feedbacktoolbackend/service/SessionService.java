package com.example.feedbacktoolbackend.service;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.exception.AuthorisationException;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.data.models.Session;
import com.example.feedbacktoolbackend.data.models.User;
import com.example.feedbacktoolbackend.data.SessionRepository;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import com.example.feedbacktoolbackend.util.factory.SessionFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;


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
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionFactory sessionFactory;
    private final UserFactory userFactory;

    @Autowired
    public SessionService(PasswordEncodingService passwordEncodingService, UserRepository userRepository,
                          SessionRepository sessionRepository, SessionFactory sessionFactory, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.sessionFactory = sessionFactory;
        this.userFactory = userFactory;
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
            UserBusiness user = retrieveUserForAuthentication(loginDTO);
            SessionBusiness sessionBusiness = createSession(user);
            return sessionBusiness.getSessionId();
        } catch (CustomHttpException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
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
        return getUserByEmail(user.getEmail());
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
        return userFactory.convertToBusinessModel(userEntity);
    }

    /**
     * Creates a session for the authenticated user using SessionFactory.
     *
     * @param userBusiness UserBusiness object
     * @return SessionBusiness object representing the created session
     * @author Sven Molenaar
     */
    private SessionBusiness createSession(UserBusiness userBusiness) {
        SessionBusiness session = new SessionBusiness(null, userBusiness, Date.from(Instant.now()));
        session = sessionFactory.createBusinessModel(sessionRepository.save(sessionFactory.createDataEntity(session)));
        return session;
    }

    /**
     * Authorizes a user based on the provided session ID.
     *
     * @param sessionId The session ID used for authorization.
     * @return A UserBusiness instance authorized via the provided session ID.
     * @throws AuthorisationException If an invalid session ID or unauthorized access occurs.
     * @author Sven Molenaar
     */
    public UserBusiness authoriseBySessionId(String sessionId) throws AuthorisationException {
        try {
            SessionBusiness sessionBusiness = getSessionBySessionId(sessionId);
            return sessionBusiness.getUser();
        } catch (IllegalArgumentException e) {
            throw new AuthorisationException("Invalid session ID");
        }
    }

    /**
     * Retrieves a SessionBusiness model based on the provided session ID.
     *
     * @param sessionId The session ID used to fetch the SessionBusiness model.
     * @return A SessionBusiness instance fetched by the session ID.
     * @author Sven Molenaar
     */
    private SessionBusiness getSessionBySessionId(String sessionId) {
        return sessionFactory.createBusinessModel(sessionRepository.getReferenceById(UUID.fromString(sessionId)));
    }
}
