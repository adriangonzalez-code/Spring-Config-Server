package com.driagon.services.configserver.services;

public interface IScopeKeyAuthService {

    /**
     * Validates the scope key for the given scope name.
     *
     * @param scopeName the name of the scope
     * @param accessKey the access key to validate
     * @return true if the access key is valid for the given scope, false otherwise
     */
    boolean isValid(String scopeName, String accessKey);
}