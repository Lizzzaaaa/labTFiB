package com.example.labTFiB.product;


import com.example.labTFiB.category.Category;
import com.example.labTFiB.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }
    @Override
    public List<Product>findAllProductsByCategory(Category categories){
        return productRepository.findByBookCategory(categories);
    }
    //    @Override
//    public List<Product> findAllProductsByCategoryId(Long categoryId){
//        return productRepository.findByCategoryId(categoryId);
//    }
    @Override
    public Optional<Product> findAllProductsById(Long bookId){
        return productRepository.findById(bookId);
    }

    @Override
    public List<Product> findAllProductsByCategory(String categories) {
        return List.of();
    }
    @Override
    public Product updateProduct(Long id, ProductUpdateRequest productUpdateRequest){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " does not exist"));
        if (productUpdateRequest.getTitle() != null) {
            existingProduct.setTitle(productUpdateRequest.getTitle());
        }
        if (productUpdateRequest.getYear() != null) {
            existingProduct.setYear(productUpdateRequest.getYear());
        }
        if (productUpdateRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productUpdateRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category with ID " + productUpdateRequest.getCategoryId() + " does not exist"));
            //existingProduct.setMovieCategory(category);
        }
        if (productUpdateRequest.getMovieDescription() != null) {
            existingProduct.setMovieDescription(productUpdateRequest.getMovieDescription());
        }
        if (productUpdateRequest.getPrice() != null) {
            existingProduct.setPrice(productUpdateRequest.getPrice());
        }

        return productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product with ID " + id + " not found"));
        productRepository.delete(product);
    }

    @Override
    public Product addProduct(ProductCreationRequest request){
        Product product = new Product(
                request.getTitle(),
                request.getYear(),
                request.getCategoryName(),
                request.getDescription(),
                request.getPrice()
        );
        return productRepository.save(product);
    }
}
