package com.example.feedbacktoolbackend.util.factory;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.data.models.User;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.PasswordEncodingService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    private final PasswordEncodingService passwordEncoderService;

    @Autowired
    public UserFactory(PasswordEncodingService passwordEncoderService) {
        this.passwordEncoderService = passwordEncoderService;
    }

    public UserBusiness convertToBusinessModel(User userEntity) {
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

    public User convertToDataEntity(UserBusiness userBusiness) {
        User userEntity = new User();
        userEntity.setId(userBusiness.getId());
        userEntity.setFirstName(userBusiness.getFirstName());
        userEntity.setPrefixes(userBusiness.getPrefixes());
        userEntity.setLastName(userBusiness.getLastName());
        userEntity.setEmail(userBusiness.getEmail());
        userEntity.setPassword(userBusiness.getPassword());
        userEntity.setRole(userBusiness.getRole());
        return userEntity;
    }

    public UserBusiness createUserBusinessFromDTO(RegistrationDTO registrationDTO) throws CustomHttpException {
        return new UserBusiness(
                registrationDTO.firstName(),
                registrationDTO.prefixes(),
                registrationDTO.lastName(),
                registrationDTO.email(),
                passwordEncoderService.encodePassword(registrationDTO.password()),
                Role.STUDENT
        );
    }
    public UserBusiness createBusinessModel(User userEntity) {
        return new UserBusiness(userEntity.getId(), userEntity.getFirstName(), userEntity.getPrefixes(), userEntity.getLastName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getRole());
    }
}

