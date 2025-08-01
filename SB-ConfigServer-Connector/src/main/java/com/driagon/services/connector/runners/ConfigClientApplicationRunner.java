package com.driagon.services.connector.runners;

import com.driagon.services.connector.constants.LoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigClientApplicationRunner implements ApplicationRunner, Ordered {

    private final ConfigurableEnvironment environment;

    public ConfigClientApplicationRunner(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info(LoggingConstants.RUNNER_STARTED);

        boolean hasConfigProperties = environment.getPropertySources()
                .stream()
                .anyMatch(ps -> ps.getName().equals("config-server-properties"));

        if (hasConfigProperties) {
            log.info(LoggingConstants.RUNNER_PROPERTIES_DETECTED);

            String url = environment.getProperty("config.url");
            String scope = environment.getProperty("config.scope");
            log.info(LoggingConstants.RUNNER_ACTIVE_CONFIG, url, scope);

        } else {
            log.warn(LoggingConstants.RUNNER_NO_PROPERTIES);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}