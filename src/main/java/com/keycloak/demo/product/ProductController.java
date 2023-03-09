package com.keycloak.demo.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productServiceImpl;

    @GetMapping
    public ResponseEntity<?> getProducts(Authentication authentication) {

        KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String username = accessToken.getPreferredUsername();

        log.info("username: {}", username);

        List<ProductDTO> products = productServiceImpl.findAll();

        return ResponseEntity.ok(products);
    }

    // https://www.baeldung.com/spring-security-expressions

    @PostMapping
//    @PreAuthorize("hasRole('SELLER')")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productRequest) {
        ProductDTO product = productServiceImpl.save(productRequest);
        return ResponseEntity.ok(product);
    }

}
