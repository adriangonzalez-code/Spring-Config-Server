package com.driagon.services.configserver.dto.requests;

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
public class SetPropertyRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5879205714421235636L;

    private Long id;

    private String key;

    private String value;

    private boolean secret;
}