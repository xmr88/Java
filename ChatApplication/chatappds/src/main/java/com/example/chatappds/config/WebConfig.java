package com.example.chatappds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("http://localhost:4200") // Allow only Angular frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Restrict to necessary methods
                .allowedHeaders("Content-Type", "Authorization") // Restrict to necessary headers
                .allowCredentials(true); // Allow cookies or authentication headers
    }
}