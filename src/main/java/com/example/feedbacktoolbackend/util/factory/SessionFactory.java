package com.example.feedbacktoolbackend.util.factory;
/**
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.data.models.Session;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory implements ModelFactory<SessionBusiness, Session> {
    private final UserFactory userFactory;

    public SessionFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
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

    public Session createDataEntity(SessionBusiness sessionBusiness) {
        return new Session(userFactory.createDataEntity(sessionBusiness.getUser()), sessionBusiness.getCreatedAt());
    }
}
