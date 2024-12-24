package com.example.labTFiB.product;

import com.example.labTFiB.category.Category;
import com.example.labTFiB.category.CategoryRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/products")
@RequiredArgsConstructor
@Builder
public class ProductController {
    private ProductMapper productMapper;
    private final ProductService productService;
    private CategoryRepository categoriesRepository;


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody
                                 @Validated ProductCreationRequest productCreationRequest){
        Category category;
        Optional<Category> optionalCategories = Optional.ofNullable(categoriesRepository.findByName(String.valueOf(productCreationRequest.getCategoryName())));
        if (optionalCategories.isPresent()){
            category = optionalCategories.get();
        } else {
            category = new Category(String.valueOf(productCreationRequest.getCategoryName()), "Opis");
            category = categoriesRepository.save(category);
        }

        Product newProduct = productService.addProduct(productCreationRequest);
        return new ProductDto(newProduct.getTitle(), newProduct.getYear(), newProduct.getMovieCategory(), newProduct.getMovieDescription(), newProduct.getPrice());
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.findAllProducts()
                .stream()
                .map(productMapper::productDto)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"/category"})
    public List<ProductDto> getProductsByCategory(@RequestParam String category){
        return productService.findAllProductsByCategory(category).stream()
                .map(productMapper::productDto)
                .collect(Collectors.toList());
    }


    @GetMapping(path = {"id"})
    public List<ProductDto> getProductsById(@PathVariable("id") Long id){
        return productService.findAllProductsById(id).stream()
                .map(productMapper::productDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct (@PathVariable Long id, @RequestBody @Validated ProductUpdateRequest productUpdateRequest){
        Product updatedProduct = productService.updateProduct(id, productUpdateRequest);
        return new ProductDto(
                updatedProduct.getTitle(),
                updatedProduct.getYear(),
                updatedProduct.getMovieCategory(),
                updatedProduct.getMovieDescription(),
                updatedProduct.getPrice()
        );
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getProductByCategory(@PathVariable String categoryName){
        Category category = categoriesRepository.findByName(categoryName);
        return productService.findAllProductsByCategory(category);
    }
}
