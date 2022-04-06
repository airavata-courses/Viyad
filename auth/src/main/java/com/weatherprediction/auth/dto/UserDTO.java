package com.weatherprediction.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.weatherprediction.auth.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Role role;
}
