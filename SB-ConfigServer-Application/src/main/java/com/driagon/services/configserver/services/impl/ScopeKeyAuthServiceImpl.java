package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.repositories.IScopeRepository;
import com.driagon.services.configserver.services.IScopeKeyAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScopeKeyAuthServiceImpl implements IScopeKeyAuthService {

    private final IScopeRepository scopeRepository;

    /**
     * Validates the scope key for the given scope name.
     *
     * @param scopeName the name of the scope
     * @param accessKey the access key to validate
     * @return true if the access key is valid for the given scope, false otherwise
     */
    @Override
    public boolean isValid(String scopeName, String accessKey) {
        return this.scopeRepository.existsScopeByNameAndAccessKey(scopeName, accessKey);
    }
}