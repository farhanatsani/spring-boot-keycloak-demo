package com.keycloak.demo.keycloak;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

@Service
public class KeycloakClientService {
    @Value("${keycloak.credentials.secret}")
    private String secretKey;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Value("${keycloak.realm}")
    private String realm;
    public AuthzClient authzClient(){
        HashMap<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", secretKey);
        Configuration configuration =
                new Configuration(authUrl, realm, clientId, clientCredentials, null);
        return AuthzClient.create(configuration);
    }
    public String getRefreshTokenUrl(){
        return authUrl+"/realms/"+realm+"/protocol/openid-connect/token";
    }
    public MultiValueMap<String, String> getClient(){
        MultiValueMap map =  new LinkedMultiValueMap<>();
        map.set("client_id", clientId);
        map.set("client_secret", secretKey);
        return map;
    }
}
