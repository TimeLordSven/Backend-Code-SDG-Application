package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
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

    public String login(LoginDTO loginDTO) {
        try {
            validateAuthenticationDTO(loginDTO);

            UserBusiness user = retrieveUserForAuthentication(loginDTO);
            validatePassword(user, loginDTO.password());

            SessionBusiness sessionBusiness = createSession(user);
            return sessionBusiness.getSessionId();
        } catch (CustomHttpException e) {
            // Handle CustomHttpException
            // Depending on your logic, you might want to return a specific value or re-throw the exception
            throw e; // Re-throwing the exception
        } catch (Exception e) {
            throw new CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void validateAuthenticationDTO(LoginDTO dto) {
        if (dto.email() == null || dto.email().isBlank() || dto.password() == null || dto.password().isBlank()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Input cannot be empty");
        }
    }

    private UserBusiness retrieveUserForAuthentication(LoginDTO loginDTO) {
        UserBusiness user = new UserBusiness(loginDTO.email());
        validateEmail(user);
        return getUserByEmail(user.getEmail());
    }

    private void validateEmail(UserBusiness userBusiness) {
        if (!userBusiness.hasValidEmail()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
    }

    private UserBusiness getUserByEmail(String email) {
        User userEntity = userRepository.getUserByEmail(email);
        if (userEntity == null) {
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return converter.convertToBusinessModel(userEntity);
    }

    private void validatePassword(UserBusiness userBusiness, String rawPassword) {
        passwordEncodingService.validatePassword(rawPassword, userBusiness.getPassword());
    }

    private SessionBusiness createSession(UserBusiness userBusiness) {
        Session session = new Session();
        session.setUser(converter.convertToDataEntity(userBusiness));
        sessionRepository.save(session);
        return converter.convertToBusinessModel(session);
    }
}
