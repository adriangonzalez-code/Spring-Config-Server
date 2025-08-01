package com.driagon.services.configserver.dto.requests;

import com.driagon.services.configserver.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CreateScopeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -796428478372724967L;

    @NotBlank(message = ValidationMessages.CreateScope.NAME_NOT_BLANK)
    @Size(min = 3, max = 100, message = ValidationMessages.CreateScope.NAME_LENGTH)
    private String scopeName;

    @Size(max = 255, message = ValidationMessages.CreateScope.DESCRIPTION_LENGTH)
    private String description;
}