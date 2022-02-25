package com.dikann.webservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    private @NotNull String username;
    private @NotNull String password;

}
