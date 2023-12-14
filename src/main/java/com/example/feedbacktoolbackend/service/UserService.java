package com.example.feedbacktoolbackend.service;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.AlreadyExistsException;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     *
     * @param registrationDTO Authentication data to create a user
     * @return UserBusiness object representing the created user
     * @throws AlreadyExistsException if a user with the provided email already exists
     * @throws InvalidInputException  if input data is invalid or doesn't match criteria
     * @author Sven Molenaar
     */
    public UserBusiness createUser(RegistrationDTO registrationDTO) throws AlreadyExistsException, InvalidInputException, CustomHttpException {
        validatePasswords(registrationDTO.password(), registrationDTO.verifyPassword());
        UserBusiness userBusiness = createUserBusinessFromDTO(registrationDTO);
        validateUser(userBusiness);
        return convertToUserBusiness(repository.save(convertToUserEntity(userBusiness)));
    }

    public void validatePasswords(String password, String verifyPassword) {
        passwordEncoderService.validateInputPasswordRegister(password, verifyPassword);
    }

    /**
     * Validates the given name against various criteria.
     *
     * @param name      The name to validate
     * @param fieldName The name of the field being validated
     * @throws CustomHttpException When the validation fails
     * @author Sven Molenaar
     */

    private void validateName(String name, String fieldName) throws CustomHttpException {
        if (name == null || name.isEmpty()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, fieldName + " cannot be null or an empty string");
        }

        if (name.length() < 2) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, fieldName + " should be at least 1 character long");
        }

        if (name.length() > 50) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, fieldName + " cannot be longer than 50 characters");
        }

        if (name.contains(" ")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, fieldName + " cannot contain spaces");
        }

        if (!name.matches("^[a-zA-Z]+$")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, fieldName + " can only contain alphabetic characters");
        }
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
        String firstName = registrationDTO.firstName();
        String prefixes = registrationDTO.prefixes();
        String lastName = registrationDTO.lastName();
        String email = registrationDTO.email();
        String password = passwordEncoderService.encodePassword(registrationDTO.password());
        Role role = Role.STUDENT;

        validateName(firstName, "First name");
        validateName(lastName, "Last name");
        /**
         * Regex Explanation
         * String only contains alphabetic characters, regardless of case.
         */
        if (prefixes != null && !prefixes.isEmpty() && !prefixes.matches("^[a-zA-Z]+$")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Prefix can only contain alphabetic characters");
        }

        UserBusiness userBusiness = new UserBusiness(firstName, prefixes, lastName, email, password, role);

        if (!userBusiness.hasValidEmail()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, userBusiness.getEmail() + " is not a valid email");
        }

        return userBusiness;
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
            throw new CustomHttpException(HttpStatus.CONFLICT, " E-mail is already taken");
        }
    }

    /**
     * Converts a UserBusiness object to a User entity.
     *
     * @param userBusiness UserBusiness object to convert
     * @return User entity
     * @author Sven Molenaar
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
}