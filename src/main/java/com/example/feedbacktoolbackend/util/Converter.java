package com.example.feedbacktoolbackend.util;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.data.Models.Session;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    /**
     * Converts a UserBusiness object to a User entity.
     *
     * @param userBusiness UserBusiness object to convert
     * @return User entity
     * @author Sven Molenaar
     */
    public User convertToDataEntity(UserBusiness userBusiness) {
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
     * Converts a Session entity to a SessionBusiness object.
     *
     * @param sessionEntity Session entity to convert
     * @return SessionBusiness object
     * @author Sven Molenaar
     */
    public SessionBusiness convertToBusinessModel(Session sessionEntity) {
        return new SessionBusiness(
                sessionEntity.getSessionId().toString(),
                convertToBusinessModel(sessionEntity.getUser()),
                sessionEntity.getCreatedAt()
        );
    }
}
