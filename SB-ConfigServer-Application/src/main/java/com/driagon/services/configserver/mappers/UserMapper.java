package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "active", constant = "true"),
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "password", source = "password"),
        @Mapping(target = "role.id", source = "roleId")
    })
    User mapUserRequestToUserEntity(UserRequest request);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "roleName", source = "role.name")
    })
    UserResponse mapUserEntityToUserResponse(User user);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "active", source = "active"),
        @Mapping(target = "password", ignore = true)
    })
    void mapUpdateUserRequestToUserEntity(UpdateUserRequest request, @MappingTarget User user);
}