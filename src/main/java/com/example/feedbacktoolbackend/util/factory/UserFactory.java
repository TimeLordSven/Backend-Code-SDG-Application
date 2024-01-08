package com.example.feedbacktoolbackend.util.factory;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    /**
     * Converts a User entity to a UserBusiness object.
     *
     * @param userEntity User entity to convert
     * @return UserBusiness object
     * @author Sven Molenaar
     */
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

    /**
     * Converts a UserBusiness object to a User entity.
     *
     * @param userBusiness UserBusiness object to convert
     * @return User entity representing the converted UserBusiness
     * @author Sven Molenaar
     */
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


}
