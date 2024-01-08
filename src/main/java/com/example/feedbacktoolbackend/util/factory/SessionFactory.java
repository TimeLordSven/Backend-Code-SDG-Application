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
}
