package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.entities.Property;
import com.driagon.services.configserver.utils.EncryptionUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PropertyMapper {

    @Autowired
    protected EncryptionUtil encryptionUtil;

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "key", source = "key"),
            @Mapping(target = "value", expression = "java(processValue(request.getValue(), request.isSecret()))"),
            @Mapping(target = "secret", source = "secret"),
            @Mapping(target = "scope", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    public abstract Property mapRequestToProperty(SetPropertyRequest request);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "key", source = "key"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "secret", source = "secret")
    })
    public abstract SetPropertyResponse mapPropertyEntityToSetPropertyResponse(Property properties);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "key", source = "key"),
            @Mapping(target = "value", expression = "java(decryptValue(property.getValue(), property.isSecret()))"),
            @Mapping(target = "secret", ignore = true)
    })
    public abstract SetPropertyResponse mapPropertyEntityToSetPropertyResponseWithDecryption(Property property);

    protected String processValue(String value, boolean isSecret) {
        if (isSecret && value != null && !value.trim().isEmpty()) {
            return encryptionUtil.encrypt(value);
        }
        return value;
    }

    protected String decryptValue(String value, boolean isSecret) {
        if (isSecret && value != null && !value.trim().isEmpty()) {
            return encryptionUtil.decrypt(value);
        }
        return value;
    }
}