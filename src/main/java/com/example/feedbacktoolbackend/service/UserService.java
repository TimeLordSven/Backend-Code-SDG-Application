package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.AlreadyExistsException;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.enums.Role;
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

    @Autowired
    public UserService(PasswordEncodingService passwordEncoderService, UserRepository repository) {
        this.passwordEncoderService = passwordEncoderService;
        this.repository = repository;
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
        UserBusiness userBusiness = createUserBusinessFromDTO(registrationDTO);
        validateUser(userBusiness);
        return convertToUserBusiness(repository.save(convertToUserEntity(userBusiness)));
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
     * Validates if a user with the same email already exists.
     *
     * @param userBusiness The UserBusiness object to check for existing email
     * @throws CustomHttpException if a user with the same email already exists
     * @author Sven Molenaar
     */
    private void validateUser(UserBusiness userBusiness) throws CustomHttpException {
        if (repository.existsByEmail(userBusiness.getEmail())) {
            throw new CustomHttpException(HttpStatus.CONFLICT, "E-mail is already taken");
        }
    }

    /**
     * Converts a UserBusiness object to a User entity.
     *
     * @param userBusiness UserBusiness object to convert
     * @return User entity
     * @throws IllegalArgumentException if the provided User entity is null
     * @author Sven Molenaar
     */
    private User convertToUserEntity(UserBusiness userBusiness) {
        if (userBusiness == null) {
            throw new IllegalArgumentException("UserBusiness is null");
        }
        User user = new User();
        user.setId(userBusiness.getId());
        user.setFirstName(userBusiness.getFirstName());
        user.setPrefixes(userBusiness.getPrefixes());
        user.setLastName(userBusiness.getLastName());
        user.setEmail(userBusiness.getEmail());
        user.setPassword(userBusiness.getPassword());
        user.setRole(userBusiness.getRole());
        return user;
    }

    /**
     * Converts a User entity to a UserBusiness object.
     *
     * @param userEntity User entity to convert
     * @return UserBusiness object
     * @throws IllegalArgumentException if the provided User entity is null
     * @author Sven Molenaar
     */
    private UserBusiness convertToUserBusiness(User userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("UserEntity is null");
        }
        return new UserBusiness(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getPrefixes(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }

    /**
     * Creates a UserBusiness object from the provided RegistrationDTO.
     *
     * @param registrationDTO The DTO containing user registration details
     * @return UserBusiness object created from the DTO
     * @throws CustomHttpException When the input is invalid
     * @author Sven Molenaar
     */
    private UserBusiness createUserBusinessFromDTO(RegistrationDTO registrationDTO) throws CustomHttpException {
        UserBusiness userBusiness = new UserBusiness(
                registrationDTO.firstName(),
                registrationDTO.prefixes(),
                registrationDTO.lastName(),
                registrationDTO.email(),
                passwordEncoderService.encodePassword(registrationDTO.password()),
                Role.STUDENT
        );

        return userBusiness;
    }
}
