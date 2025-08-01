package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.responses.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    @Mappings({
            @Mapping(target = "accessToken", source = "token")
    })
    public abstract AuthResponse mapTokenToAuthResponse(String token);
}