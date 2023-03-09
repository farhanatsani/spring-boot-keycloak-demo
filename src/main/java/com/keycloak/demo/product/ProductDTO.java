package com.keycloak.demo.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @Builder
public class ProductDTO {
    private UUID id;
    private String productName;
    private BigDecimal price;
}
