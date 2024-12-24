package com.example.labTFiB.product;

import com.example.labTFiB.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBookCategory(Category category);
}
