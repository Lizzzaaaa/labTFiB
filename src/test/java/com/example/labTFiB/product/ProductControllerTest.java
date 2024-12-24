package com.example.labTFiB.product;

import com.example.labTFiB.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productServiceMock;
    private MockMvc mockMvc;

    @BeforeEach
    void setUpProductControllerTest(){
        ProductController controller = new ProductController(productServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void  shouldReturnAllProducts_whenGettingAllProducts() throws Exception {
        Category category =
                Category.builder().categoriesId(1L).description("deck").name("fantasy").build();
        Product product =
                Product.builder().price(20D).title("Echo").movieCategory("fantasy").build();
        when(productServiceMock.findAllProducts()).thenReturn(List.of(product));
        mockMvc.perform(get("/rest/products"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").isArray())
                .andExpect((ResultMatcher) jsonPath("$[0].title").value(product.getTitle()))
                .andExpect((ResultMatcher) jsonPath("$[1]").doesNotExist());
    }
    @Test
    void shouldReturnCreatedProduct_whenCreatingProduct() throws Exception {
        ProductCreationRequest expectedRequest = new ProductCreationRequest(
                "Wonderland",
                2007L,
                "fantasy",
                "nice movie",
                20.00);
        Product product = new Product("Wonderland", 2007L, "fantasy", "nice movie", 20.00);

        when(productServiceMock.addProduct(expectedRequest)).thenReturn(product);
        String requestBody = """ 
                             { 
                               "category": %s, 
                               "name": "%s" 
                               "title":  "Wonderland"
                               "year": 2007
                               "categoryName": "fantasy"
                               "description": "nice movie"
                               "price": 20.00
                             } 
                             """.formatted("Megaland",
                2007,
                "fantasy",
                "nice movie",
                20.00);


        mockMvc.perform(post("/rest/products/add").contentType(MediaType.APPLICATION_JSON
                        )
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name").value(product.getTitle()));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        Long productId = 1L;
        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "Updated Title",
                2011L,
                "fantasy",
                "Updated Description",
                199.99
        );
        Product updatedProduct = new Product(
                "Updated Title",
                2011L,
                "Updated Category",
                "Updated Description",
                199.99
        );

        // Mock the service behavior
        when(productServiceMock.updateProduct(eq(productId), any(ProductUpdateRequest.class))).thenReturn(updatedProduct);
        String updateRequestTest = ("""
                                {
                                    "category": %s, 
                                    "name": "%s" 
                                    "title": "Updated Title",
                                    "year": 2011,
                                    "categoryId": 2,
                                    "description": "Updated Description",
                                    "price": 199.99
                                }
                                """);
        mockMvc.perform((RequestBuilder) patch("/rest/products/{id}", productId).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(updateRequestTest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title", is(updatedProduct.getTitle())))
                .andExpect((ResultMatcher) jsonPath("$.year", is(updatedProduct.getYear())))
                .andExpect((ResultMatcher) jsonPath("$.movieCategory", is(updatedProduct.getMovieCategory())))
                .andExpect((ResultMatcher) jsonPath("$.movieDescription", is(updatedProduct.getMovieCategory())))
                .andExpect((ResultMatcher) jsonPath("$.price", is(updatedProduct.getPrice())));
    }
}