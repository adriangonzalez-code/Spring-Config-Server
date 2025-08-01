package com.driagon.services.connector.processors;

import com.driagon.services.connector.clients.ConfigPropertiesClient;
import com.driagon.services.connector.constants.LoggingConstants;
import com.driagon.services.connector.models.responses.PropertyResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigClientEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String url = environment.getProperty("config.url");
        String scope = environment.getProperty("config.scope");
        String accessKey = environment.getProperty("config.key");

        if (scope == null || accessKey == null || url == null) {
            System.out.println(LoggingConstants.PROCESSOR_CONFIG_MISSING);
            return;
        }

        try {
            System.out.println(LoggingConstants.PROCESSOR_LOADING_PROPERTIES);
            Set<PropertyResponse> properties = new ConfigPropertiesClient().fetchProperties(url, scope, accessKey);

            Map<String, Object> props = new HashMap<>();
            for (PropertyResponse prop : properties) {
                props.put(prop.getKey(), prop.getValue());
            }

            MapPropertySource propertySource = new MapPropertySource("config-server-properties", props);
            environment.getPropertySources().addFirst(propertySource);

            System.out.printf(LoggingConstants.PROCESSOR_PROPERTIES_LOADED, props.size());

        } catch (Exception e) {
            System.err.printf(LoggingConstants.PROCESSOR_ERROR_LOADING_PROPERTIES, e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}