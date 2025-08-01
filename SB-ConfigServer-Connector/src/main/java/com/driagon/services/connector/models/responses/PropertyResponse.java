package com.driagon.services.connector.models.responses;

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
@Builder
@ToString
public class PropertyResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -689703783216361050L;

    private String key;
    private String value;
}