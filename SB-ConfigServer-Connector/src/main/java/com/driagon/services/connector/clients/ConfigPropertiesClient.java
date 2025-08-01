package com.driagon.services.connector.clients;

import com.driagon.services.connector.constants.LoggingConstants;
import com.driagon.services.connector.models.responses.PropertyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class ConfigPropertiesClient {

    private final static Logger log = LoggerFactory.getLogger(ConfigPropertiesClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public Set<PropertyResponse> fetchProperties(String url, String scope, String accessKey) {
        log.info(LoggingConstants.CLIENT_FETCHING_PROPERTIES, url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Scope-Name", scope);
        headers.set("X-Scope-Access-Key", accessKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Set<PropertyResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        Set<PropertyResponse> properties = response.getBody();

        if (properties != null) {
            log.info(LoggingConstants.CLIENT_PROPERTIES_FETCHED_SUCCESS, properties.size());
        }

        return properties;
    }
}