package com.example.labTFiB.category;

public class CategoryMapper {
    public CategoryDto categoryDto (Category category){
        return new CategoryDto(category.getName(), category.getDescription());
    }
}