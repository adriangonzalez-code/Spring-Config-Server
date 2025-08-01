package com.driagon.services.configserver.dto.requests;

import com.driagon.services.configserver.constants.ValidationMessages;
import com.driagon.services.logging.annotations.Exclude;
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
public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6098312281190306904L;

    @NotBlank(message = ValidationMessages.CreateUser.FIRST_NAME_NOT_BLANK)
    private String firstName;

    @NotBlank(message = ValidationMessages.CreateUser.LAST_NAME_NOT_BLANK)
    private String lastName;

    @NotBlank(message = ValidationMessages.CreateUser.EMAIL_NOT_BLANK)
    @Mask
    private String email;

    @NotBlank(message = ValidationMessages.CreateUser.PASSWORD_NOT_BLANK)
    @Exclude
    private String password;

    @Positive(message = ValidationMessages.CreateUser.ROLE_ID_POSITIVE)
    private Long roleId;
}