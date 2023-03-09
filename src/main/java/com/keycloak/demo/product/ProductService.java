package com.keycloak.demo.product;

import java.util.List;

public interface ProductService {
    ProductDTO save(ProductDTO productDTO);
    List<ProductDTO> findAll();
}
