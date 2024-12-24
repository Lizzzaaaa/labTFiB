package com.example.labTFiB.category;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
@RequiredArgsConstructor
@Builder
public class CategoryController {

    private CategoryMapper categoriesMapper;
    private final CategoryService categoriesService;

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return categoriesService.findAllCategories()
                .stream()
                .map(categories -> new CategoryDto(
                        categories.getName(),
                        categories.getDescription()))
                .toList();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Validated CategoryCreationRequest categoriesCreationRequest){
        Category categories = categoriesService.addCategory(categoriesCreationRequest);
        return new CategoryDto(categories.getName(), categories.getDescription());
    }

}
