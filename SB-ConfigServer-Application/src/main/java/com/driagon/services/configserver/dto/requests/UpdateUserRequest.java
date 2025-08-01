package com.driagon.services.configserver.dto.requests;

import com.driagon.services.configserver.constants.ValidationMessages;
import com.driagon.services.logging.annotations.Mask;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class UpdateUserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2576025351115485544L;

    private Long id;

    @NotBlank(message = ValidationMessages.CreateUser.FIRST_NAME_NOT_BLANK)
    private String firstName;

    @NotBlank(message = ValidationMessages.CreateUser.LAST_NAME_NOT_BLANK)
    private String lastName;

    private boolean active;

    @Mask
    @NotBlank(message = ValidationMessages.CreateUser.EMAIL_NOT_BLANK)
    private String email;

    @Positive(message = ValidationMessages.CreateUser.ROLE_ID_POSITIVE)
    private Long roleId;
}