package com.driagon.services.configserver.configs;

import com.driagon.services.logging.aspects.LoggingAspect;
import com.driagon.services.logging.services.LoggingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLoggingConfiguration {

    @Bean
    public LoggingService customLoggingService() {
        return new LoggingService();
    }

    @Bean
    public LoggingAspect customLoggingAspect(LoggingService loggingService) {
        return new LoggingAspect(loggingService);
    }
}