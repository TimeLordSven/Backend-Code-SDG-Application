package com.example.feedbacktoolbackend.util;

import com.example.feedbacktoolbackend.data.Models.Session;
import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.stereotype.Component;

@Component
public class Converter {
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

    public SessionBusiness convertToBusinessModel(Session sessionEntity) {
        return new SessionBusiness(
                sessionEntity.getSessionId().toString(),
                convertToBusinessModel(sessionEntity.getUser()),
                sessionEntity.getCreatedAt()
        );
    }
}
