package com.example.SpringBootDemo.controller;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class UserLoginRequest {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    public UserLoginRequest() {
    }

    public UserLoginRequest(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }
}