package com.example.feedbacktoolbackend.util.factory;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.data.Models.Session;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {
    private final UserFactory userFactory;

    public SessionFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
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

                userFactory.convertToBusinessModel(sessionEntity.getUser()),
                sessionEntity.getCreatedAt()
        );
    }
    /**
     * Creates a SessionBusiness model based on the provided Session entity.
     *
     * @param sessionEntity The Session entity used to create the SessionBusiness model.
     * @return A SessionBusiness instance created from the Session entity.
     * @author Sven Molenaar
     */
    public SessionBusiness createBusinessModel(Session sessionEntity) {
        return new SessionBusiness(sessionEntity.getSessionId().toString(), userFactory.createBusinessModel(sessionEntity.getUser()), sessionEntity.getCreatedAt());
    }
}
