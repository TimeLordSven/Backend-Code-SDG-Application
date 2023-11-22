package com.example.feedbacktoolbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * Creates and configures a CORS (Cross-Origin Resource Sharing) filter bean.
     * This method sets up CORS configuration to allow specific origins, methods, and headers.
     * It registers the configured CORS settings to be applied to all endpoints (/**).
     * @author Sven Molenaar
     * @return CorsFilter bean configured with CORS settings.
     */
    @Bean
    public CorsFilter CorsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Enable credentials for cross-origin requests
        config.setAllowCredentials(true);

        // Allow requests from a specific origin (http://localhost:4200)
        config.addAllowedOrigin("http://localhost:4200");

        // Allow specific HTTP methods: GET, POST, PUT, DELETE
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // Allow specific HTTP headers: Content-Type, Origin, Accept
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Accept");

        // Register the CORS configuration for all endpoints
        source.registerCorsConfiguration("/**", config);

        // Create and return a CorsFilter with the configured source
        return new CorsFilter(source);
    }
}
