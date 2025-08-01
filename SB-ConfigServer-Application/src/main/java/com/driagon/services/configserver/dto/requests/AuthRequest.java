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
public class AuthRequest  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1282694043213574532L;

    private String email;
    private String password;
}