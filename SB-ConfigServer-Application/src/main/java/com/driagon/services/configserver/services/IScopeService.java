package com.driagon.services.configserver.services;

import com.driagon.services.configserver.dto.requests.CreateScopeRequest;
import com.driagon.services.configserver.dto.responses.AccessKeyResponse;
import com.driagon.services.configserver.dto.responses.CreateScopeResponse;
import com.driagon.services.configserver.dto.responses.ScopeResponse;

import java.util.Set;

public interface IScopeService {

    Set<ScopeResponse> getAllScopes();

    ScopeResponse getScopeById(Long id);

    CreateScopeResponse createScope(CreateScopeRequest request);

    boolean setUsersToScope(Long scopeId, Set<String> emails);

    AccessKeyResponse getAccessKey(Long scopeId);
}