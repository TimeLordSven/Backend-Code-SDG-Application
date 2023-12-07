package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.controller.dto.AuthenticationDTO;
import com.example.feedbacktoolbackend.controller.exception.AlreadyExistsException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Creates a new user based on the provided authentication data.
     * Validates input and checks for existing users before creation.
     * @author Sven Molenaar
     * @param authenticationDTO Authentication data to create a user
     * @return UserBusiness object representing the created user
     * @throws AlreadyExistsException if a user with the provided email already exists
     * @throws InvalidInputException if input data is invalid or doesn't match criteria
     */
    public UserBusiness createUser(AuthenticationDTO authenticationDTO) throws AlreadyExistsException, InvalidInputException {
        validatePasswords(authenticationDTO.password(), authenticationDTO.verifyPassword());
        UserBusiness userBusiness = createUserBusinessFromDTO(authenticationDTO);
        validateUser(userBusiness);
        return convertToUserBusiness(repository.save(convertToUserEntity(userBusiness)));
    }

    /**
     * Validates the passwords provided in the authentication data.
     * @author Sven molenaar
     * @param password        The password to validate
     * @param verifyPassword  The password to verify against
     * @throws InvalidInputException if passwords do not match or if the password is invalid
     */
    private void validatePasswords(String password, String verifyPassword) throws InvalidInputException {
        if (!password.equals(verifyPassword)) {
            throw new InvalidInputException("Passwords do not match");
        }

        if (!passwordEncoderService.validatePassword(password)) {
            throw new InvalidInputException("Invalid Password");
        }
    }

    /**
     * Creates a UserBusiness object from the provided AuthenticationDTO and validates name and email.
     * @author Sven Molenaar
     * @param authenticationDTO Authentication data to create a user
     * @return UserBusiness object representing the user from the DTO
     * @throws InvalidInputException if name or email is invalid
     */
    private UserBusiness createUserBusinessFromDTO(AuthenticationDTO authenticationDTO) throws InvalidInputException {
        UserBusiness userBusiness = new UserBusiness(
                authenticationDTO.firstName(),
                authenticationDTO.prefixes(),
                authenticationDTO.lastName(),
                authenticationDTO.email(),
                passwordEncoderService.encodePassword(authenticationDTO.password()),
                Role.STUDENT
        );

        if (!userBusiness.hasValidName()) {
            throw new InvalidInputException("Name can only contain alphabetical letters");
        }

        if (!userBusiness.hasValidEmail()) {
            throw new InvalidInputException(userBusiness.getEmail() + " is not a valid email");
        }

        return userBusiness;
    }

    /**
     * Validates if a user with the same email already exists.
     * @author Sven Molenaar
     * @param userBusiness The UserBusiness object to check for existing email
     * @throws AlreadyExistsException if a user with the same email already exists
     */
    private void validateUser(UserBusiness userBusiness) throws AlreadyExistsException {
        if (repository.existsByEmail(userBusiness.getEmail())) {
            throw new AlreadyExistsException("User");
        }
    }

    /**
     * Converts a UserBusiness object to a User entity.
     * @author Sven Molenaar
     * @param userBusiness UserBusiness object to convert
     * @return User entity
     */
    private User convertToUserEntity(UserBusiness userBusiness) {
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
     * @author Sven Molenaar
     * @param userEntity User entity to convert
     * @return UserBusiness object
     * @throws IllegalArgumentException if the provided User entity is null
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
}