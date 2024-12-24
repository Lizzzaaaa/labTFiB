package com.example.labTFiB.category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category addCategory(CategoryCreationRequest categoryCreationRequest);
}

