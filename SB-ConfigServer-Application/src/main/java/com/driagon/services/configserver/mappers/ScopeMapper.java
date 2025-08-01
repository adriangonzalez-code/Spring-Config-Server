package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.CreateScopeRequest;
import com.driagon.services.configserver.dto.responses.CreateScopeResponse;
import com.driagon.services.configserver.dto.responses.ScopeResponse;
import com.driagon.services.configserver.entities.Scope;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ScopeMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "scopeName", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "createdBy", source = "createdBy.email"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "users", ignore = true)
    })
    Set<ScopeResponse> mapScopeEntitiesToScopeResponses(Set<Scope> scopes);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "scopeName", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "createdBy", source = "createdBy.email"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "users", expression = "java(scope.getUsers().stream().map(user -> user.getEmail()).collect(java.util.stream.Collectors.toSet()))")
    })
    ScopeResponse mapScopeEntityToScopeResponse(Scope scope);

    @Mappings({
            @Mapping(target = "name", source = "scopeName"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "accessKey", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "id", ignore = true)
    })
    Scope mapCreateScopeRequestToScopeEntity(CreateScopeRequest request);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "scopeName", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "accessKey", source = "accessKey"),
    })
    CreateScopeResponse mapScopeEntityToCreateScopeResponse(Scope scope);
}