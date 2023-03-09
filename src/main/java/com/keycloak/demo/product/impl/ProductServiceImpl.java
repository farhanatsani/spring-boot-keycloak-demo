package com.keycloak.demo.product.impl;

import com.keycloak.demo.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = ProductMapper.toProduct(productDTO);
        productRepository.saveAndFlush(product);
        return ProductMapper.productDTO(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::productDTO)
                .collect(Collectors.toList());
    }
}
