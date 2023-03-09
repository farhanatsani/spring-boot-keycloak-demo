package com.keycloak.demo.auth;

import com.keycloak.demo.keycloak.KeycloakClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.ServerRequest;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final KeycloakClientService keycloakClientService;
    private final RestTemplate restTemplate;
    public AuthResponseDTO authenticate(String username, char[] password) {
        try {

            AuthzClient authzClient = keycloakClientService.authzClient();
            AccessTokenResponse authResponse = authzClient.obtainAccessToken(username, new String(password));

            return AuthResponseDTO.builder()
                    .username(username)
                    .tokenType(authResponse.getTokenType())
                    .accessToken(authResponse.getToken())
                    .refreshToken(authResponse.getRefreshToken())
                    .build();

        } catch (Exception ex){
            throw new AuthenticationServiceException("Unauthorized user!!!");
        }
    }
    public AuthResponseDTO refreshToken(String refreshToken) {
        try {
            Assert.notNull(refreshToken, "Refresh token is null");

            MultiValueMap<String, String> refreshTokenRequest = keycloakClientService.getClient();
            refreshTokenRequest.set("refresh_token", refreshToken);
            refreshTokenRequest.set("grant_type", "refresh_token");

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            headers.set(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
            HttpEntity request = new HttpEntity<>(refreshTokenRequest,headers);

            ResponseEntity<AccessTokenResponse> authResponse =  restTemplate.postForEntity(keycloakClientService.getRefreshTokenUrl(), request, AccessTokenResponse.class);
            AccessTokenResponse accessTokenResponse = authResponse.getBody();

            return AuthResponseDTO.builder()
                    .tokenType(accessTokenResponse.getTokenType())
                    .accessToken(accessTokenResponse.getToken())
                    .refreshToken(accessTokenResponse.getRefreshToken())
                    .build();

        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void logout(KeycloakSecurityContext keycloakSecurityContext, String refreshToken) {
        try {
            if (keycloakSecurityContext instanceof RefreshableKeycloakSecurityContext) {
                // unfortunately refreshToken can't be found anywhere, so it needs to be pushed by client
                RefreshableKeycloakSecurityContext ksc = (RefreshableKeycloakSecurityContext) keycloakSecurityContext;
                ServerRequest.invokeLogout(ksc.getDeployment(), refreshToken);
            }
        } catch (ServerRequest.HttpFailure | IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }



}
