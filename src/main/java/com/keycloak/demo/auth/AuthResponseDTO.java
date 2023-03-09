package com.keycloak.demo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String username;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
}
