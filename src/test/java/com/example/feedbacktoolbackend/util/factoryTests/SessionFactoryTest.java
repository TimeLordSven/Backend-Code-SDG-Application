package com.example.feedbacktoolbackend.util.factoryTests;
/**
 * Test for the SessionFactory
 * @author Sven Molenaar
 */

import com.example.feedbacktoolbackend.data.models.Session;
import com.example.feedbacktoolbackend.service.models.SessionBusiness;
import com.example.feedbacktoolbackend.util.factory.SessionFactory;
import com.example.feedbacktoolbackend.util.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
public class SessionFactoryTest {

    @Test
    public void testConvertToBusinessModel_PositiveCase() {
        Session sessionEntity = new Session();
        UUID sessionId = UUID.randomUUID();
        sessionEntity.setSessionId(sessionId);

        UserFactory userFactory = mock(UserFactory.class);
        SessionFactory sessionFactory = new SessionFactory(userFactory);

        SessionBusiness mockSessionBusiness = new SessionBusiness(sessionId.toString(), null, null);

        when(userFactory.convertToBusinessModel(sessionEntity.getUser())).thenReturn(mockSessionBusiness.getUser());

        SessionBusiness result = sessionFactory.createBusinessModel(sessionEntity);

        assertNotNull(result);
        assertEquals(sessionId.toString(), result.getSessionId());
    }
}
