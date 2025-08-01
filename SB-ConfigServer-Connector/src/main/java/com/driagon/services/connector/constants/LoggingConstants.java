package com.driagon.services.connector.constants;

public final class LoggingConstants {

    private LoggingConstants() {
    }

    public static final String CONFIG_DRIVER_PREFIX = "[CONFIG-DRIVER] ";
    public static final String CONFIG_DRIVER_EARLY_PREFIX = "[CONFIG-DRIVER-EARLY] ";

    public static final String RUNNER_STARTED = "🚀 " + CONFIG_DRIVER_PREFIX + "Config Client started successfully.";
    public static final String RUNNER_PROPERTIES_DETECTED = "✅ " + CONFIG_DRIVER_PREFIX + "Config-server properties detected and available.";
    public static final String RUNNER_NO_PROPERTIES = "⚠️ " + CONFIG_DRIVER_PREFIX + "No config-server properties detected.";
    public static final String RUNNER_ACTIVE_CONFIG = "📡 " + CONFIG_DRIVER_PREFIX + "Active configuration - URL: {}, Scope: {}";

    public static final String LISTENER_APP_READY = "🎉 " + CONFIG_DRIVER_PREFIX + "Application fully ready - verifying configuration.";
    public static final String LISTENER_CONFIG_SERVER_CONFIGURED = "✅ " + CONFIG_DRIVER_PREFIX + "Config-server configured: {}/{}";
    public static final String LISTENER_CONFIG_SERVER_NOT_CONFIGURED = "⚠️ " + CONFIG_DRIVER_PREFIX + "Config-server not configured.";

    public static final String PROCESSOR_CONFIG_MISSING = CONFIG_DRIVER_EARLY_PREFIX + "Skipping execution: 'config.url', 'config.scope', or 'config.key' is not defined.";
    public static final String PROCESSOR_LOADING_PROPERTIES = CONFIG_DRIVER_EARLY_PREFIX + "Loading initial properties...";
    public static final String PROCESSOR_PROPERTIES_LOADED = CONFIG_DRIVER_EARLY_PREFIX + "Initial properties loaded: %d%n";
    public static final String PROCESSOR_ERROR_LOADING_PROPERTIES = CONFIG_DRIVER_EARLY_PREFIX + "Error in initial load: %s%n";

    public static final String CLIENT_FETCHING_PROPERTIES = "Fetching properties from URL: {}";
    public static final String CLIENT_PROPERTIES_FETCHED_SUCCESS = "Successfully fetched {} properties from the config server.";
}