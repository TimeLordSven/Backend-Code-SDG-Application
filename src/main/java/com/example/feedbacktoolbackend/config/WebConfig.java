package com.example.feedbacktoolbackend.config;


import com.example.feedbacktoolbackend.util.annotations.AuthorisationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration class for setting up web-related configurations.
 *
 * @author Sven Molenaar
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorisationArgumentResolver authorizationArgumentResolver;

    /**
     * Constructor to initialize the WebConfig with an AuthorisationArgumentResolver.
     * @param authorizationArgumentResolver The AuthorisationArgumentResolver instance to be used.
     * @author Sven Molenaar
     */
    public WebConfig(AuthorisationArgumentResolver authorizationArgumentResolver) {
        this.authorizationArgumentResolver = authorizationArgumentResolver;
    }

    /**
     * Adds the AuthorisationArgumentResolver to the list of argument resolvers for handling method arguments.
     * @param argumentResolvers List of HandlerMethodArgumentResolver instances.
     * @author Sven Molenaar
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authorizationArgumentResolver);
    }
}

