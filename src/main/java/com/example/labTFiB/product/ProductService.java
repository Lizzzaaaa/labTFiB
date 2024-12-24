package com.example.labTFiB.product;

import com.example.labTFiB.category.Category;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> findAllProducts();
    List<Product> findAllProductsByCategory(Category category);
    Optional<Product> findAllProductsById(Long movieId);
    void deleteProduct(Long id);
    List<Product> findAllProductsByCategory(String category);
    Product updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
    Product addProduct(ProductCreationRequest productCreationRequest);
}

