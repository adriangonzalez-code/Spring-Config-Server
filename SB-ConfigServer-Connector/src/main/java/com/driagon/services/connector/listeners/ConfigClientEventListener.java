package com.driagon.services.connector.listeners;

import com.driagon.services.connector.constants.LoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigClientEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        log.info(LoggingConstants.LISTENER_APP_READY);

        String url = environment.getProperty("config.url");
        String scope = environment.getProperty("config.scope");

        if (url != null && scope != null) {
            log.info(LoggingConstants.LISTENER_CONFIG_SERVER_CONFIGURED, url, scope);
        } else {
            log.warn(LoggingConstants.LISTENER_CONFIG_SERVER_NOT_CONFIGURED);
        }
    }
}