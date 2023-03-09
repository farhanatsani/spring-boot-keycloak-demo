package com.keycloak.demo.auth;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDTO authenticationDto) {

        AuthResponseDTO authResponse = authenticationService
                .authenticate(authenticationDto.getUsername(),authenticationDto.getPassword().toCharArray());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {

        AuthResponseDTO refreshAuthResponse = authenticationService
                .refreshToken(refreshTokenRequestDTO.getRefreshToken());

        return ResponseEntity.ok(refreshAuthResponse);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {

        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        authenticationService.logout(keycloakSecurityContext, refreshTokenRequestDTO.getRefreshToken());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

}
