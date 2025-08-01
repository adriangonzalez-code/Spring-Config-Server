package com.driagon.services.configserver.services;

import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;

import java.util.Set;

public interface IPropertyService {

    Set<SetPropertyResponse> getAllPropertiesByScope(Long scopeId);

    Set<SetPropertyResponse> getAllPropertiesAndSecretDecryptedByScopeNameAndAccessKey(String scopeName, String accessKey);

    Set<SetPropertyResponse> setProperties(Long scopeRequest, Set<SetPropertyRequest> request);
}