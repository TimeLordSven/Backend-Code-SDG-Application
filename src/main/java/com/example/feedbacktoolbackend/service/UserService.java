package com.example.feedbacktoolbackend.service;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final PasswordEncodingService passwordEncoderService;
    private final UserRepository repository;
    private final UserFactory userFactory;

    @Autowired
    public UserService(PasswordEncodingService passwordEncoderService, UserRepository repository, UserFactory userFactory) {
        this.passwordEncoderService = passwordEncoderService;
        this.repository = repository;
        this.userFactory = userFactory;
    }

    /**
     * Creates a new user based on the provided registration data.
     *
     * @param registrationDTO Registration data to create a user
     * @return UserBusiness object representing the created user
     * @author Sven Molenaar
     */
    public UserBusiness createUser(RegistrationDTO registrationDTO) {
        validatePasswords(registrationDTO.password(), registrationDTO.verifyPassword());
        UserBusiness userBusiness = userFactory.createUserBusinessFromDTO(registrationDTO);
        validateUser(userBusiness);
        return userFactory.convertToBusinessModel(repository.save(userFactory.createDataEntity(userBusiness)));
    }

    /**
     * Validates the passwords during user creation.
     *
     * @param password       Password provided during registration
     * @param verifyPassword Password verification provided during registration
     * @author Sven Molenaar
     */
    public void validatePasswords(String password, String verifyPassword) {
        passwordEncoderService.validateInputPasswordRegister(password, verifyPassword);
    }

    /**
     * Validates if an user with an already existing email exists.
     *
     * @param userBusiness The UserBusiness object to check for an existing email
     * @throws CustomHttpException if a user with the same email already exists
     * @author Sven Molenaar
     */
    private void validateUser(UserBusiness userBusiness) throws CustomHttpException {
        if (repository.existsByEmail(userBusiness.getEmail())) {
            throw new CustomHttpException(HttpStatus.CONFLICT, "E-mail is already taken");
        }
    }
}

