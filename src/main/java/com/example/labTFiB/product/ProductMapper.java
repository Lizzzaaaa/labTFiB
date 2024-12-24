package com.example.labTFiB.product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto productDto(Product product){
        return new ProductDto(product.getTitle(), product.getYear(), product.getMovieCategory(), product.getMovieDescription(), product.getPrice());
    }
}
