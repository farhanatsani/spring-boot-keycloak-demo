package com.keycloak.demo.product;

public class ProductMapper {
    public static Product toProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .build();
        return product;
    }
    public static ProductDTO productDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
        return productDTO;
    }
}
