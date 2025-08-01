package com.driagon.services.configserver.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateScopeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 3346416583395052669L;

    private Long id;
    private String scopeName;
    private String description;
    private String accessKey;
}