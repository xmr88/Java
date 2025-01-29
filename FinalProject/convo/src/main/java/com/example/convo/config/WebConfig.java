package com.example.convo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Позволи всички URL пътища
                .allowedOrigins("http://localhost:4200") // Angular URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешени методи
                .allowedHeaders("*") // Разрешени хедъри
                .allowCredentials(true); // Разреши "credentials", ако е необходимо
    }
}
