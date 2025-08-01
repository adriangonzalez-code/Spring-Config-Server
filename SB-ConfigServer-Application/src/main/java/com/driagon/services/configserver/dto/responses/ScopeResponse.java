package com.driagon.services.configserver.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ScopeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4986575408669598859L;

    private Long id;
    private String scopeName;
    private String description;
    private String createdBy;
    private String createdAt;
    private Set<String> users;
}