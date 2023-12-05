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

    public UserBusiness createUser(AuthenticationDTO authenticationDTO) throws AlreadyExistsException, InvalidInputException {
        String password = authenticationDTO.password();

        if (!password.equals(authenticationDTO.verifyPassword())) {
            throw new InvalidInputException("Passwords do not match");
        }

        UserBusiness userBusiness = new UserBusiness(
                authenticationDTO.firstName(),
                authenticationDTO.prefixes(),
                authenticationDTO.lastName(),
                authenticationDTO.email(),
                passwordEncoderService.encodePassword(password),
                Role.STUDENT
        );

        if (!userBusiness.hasValidName()) {
            throw new InvalidInputException("Name can only contain alphabetical letters");
        }

        if (!passwordEncoderService.validatePassword(password)) {
            throw new InvalidInputException("Invalid Password");
        }

        if (!userBusiness.hasValidEmail()) {
            throw new InvalidInputException(userBusiness.getEmail() + " is not a valid email");
        }

        if (repository.existsByEmail(userBusiness.getEmail())) {
            throw new AlreadyExistsException("User");
        }

        return convertToUserBusiness(repository.save(convertToUserEntity(userBusiness)));
    }


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