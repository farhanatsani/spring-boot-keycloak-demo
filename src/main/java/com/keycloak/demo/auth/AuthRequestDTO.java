package com.keycloak.demo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDTO {
    private String username;
    private String password;
}
