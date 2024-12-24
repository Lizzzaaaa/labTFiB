package com.example.labTFiB.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(CategoryCreationRequest categoriesCreationRequest){
        Category category = new Category();
        category.setName(categoriesCreationRequest.getName());
        category.setDescription(categoriesCreationRequest.getDescription());
        return categoryRepository.save(category);
    }
}

