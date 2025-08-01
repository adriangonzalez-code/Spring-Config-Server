package com.driagon.services.configserver.dto.responses;

import com.driagon.services.logging.annotations.Exclude;
import com.driagon.services.logging.annotations.Mask;
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
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -2992287748743930093L;

    private Long id;
    private String firstName;
    private String lastName;

    @Mask
    private String email;

    @Exclude
    private String roleName;
}