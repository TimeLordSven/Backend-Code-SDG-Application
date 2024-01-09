package com.example.feedbacktoolbackend.util.annotations;

import com.example.feedbacktoolbackend.controller.exception.AuthorisationException;
import com.example.feedbacktoolbackend.service.SessionService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Optional;

/**
 * Resolves method arguments annotated for authorization.
 * Handles resolving UserBusiness based on session ID and annotations for authorization types.
 *
 * @author Sven Molenaar
 */
@Component
public class AuthorisationArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;

    public AuthorisationArgumentResolver(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Checks if the resolver supports the given parameter for resolution.
     *
     * @param parameter The method parameter to be resolved
     * @return True if the resolver supports the parameter, false otherwise
     * @author Sven Molenaar
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserBusiness.class)
                && (parameter.hasParameterAnnotation(AuthorizedUser.class)
                || parameter.hasParameterAnnotation(AuthorizedStudent.class)
                || parameter.hasParameterAnnotation(AuthorizedTeacher.class)
                || parameter.hasParameterAnnotation(AuthorizedAdmin.class));
    }

    /**
     * Resolves the method argument to retrieve the UserBusiness based on the session ID.
     *
     * @param parameter     The method parameter being resolved
     * @param mavContainer  The ModelAndViewContainer
     * @param webRequest    The NativeWebRequest
     * @param binderFactory The WebDataBinderFactory
     * @return The resolved UserBusiness based on the session ID
     * @author Sven Molenaar
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = extractRequest(webRequest);
        Cookie[] cookies = extractCookies(request);
        String sessionId = getSessionId(cookies);
        return getUserBySessionId(sessionId);
    }

    /**
     * Extracts the HttpServletRequest from NativeWebRequest.
     *
     * @param webRequest The NativeWebRequest
     * @return HttpServletRequest extracted from the NativeWebRequest
     * @throws AuthorisationException If the request is invalid or null
     * @author Sven Molenaar
     */
    private HttpServletRequest extractRequest(NativeWebRequest webRequest) {
        HttpServletRequest request = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new AuthorisationException("Invalid request"));
        return request;
    }

    /**
     * Extracts cookies from the HttpServletRequest.
     *
     * @param request The HttpServletRequest
     * @return An array of cookies from the request
     * @throws AuthorisationException If the cookies are invalid or null
     * @author Sven Molenaar
     */
    private Cookie[] extractCookies(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies())
                .orElseThrow(() -> new AuthorisationException("Invalid Session Id"));
        return cookies;
    }

    /**
     * Retrieves the session ID from the provided cookies.
     *
     * @param cookies The array of cookies
     * @return The session ID from the cookies
     * @throws AuthorisationException If the session ID is invalid or null
     * @author Sven Molenaar
     */
    private String getSessionId(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session_id"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthorisationException("Invalid Session Id"));
    }

    /**
     * Retrieves the UserBusiness based on the provided session ID.
     *
     * @param sessionId The session ID for authorization
     * @return The UserBusiness authorized by the session ID
     * @author Sven Molenaar
     */
    private UserBusiness getUserBySessionId(String sessionId) {
        return sessionService.authoriseBySessionId(sessionId);
    }
}
