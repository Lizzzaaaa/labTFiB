package com.example.labTFiB.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    private CategoryService categoriesServiceMock;
    private MockMvc mockMvc;

    @BeforeEach
    void setUpCategoriesControllerTest(){
        CategoryController controller = new CategoryController(categoriesServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnAllCategories_whenGettingAllCategories() throws Exception{
        Category category =
                Category.builder().build();
        when(categoriesServiceMock.findAllCategories()).thenReturn(List.of(category));
        mockMvc.perform(get("/rest/categories"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").isArray())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(category.getName()))
                .andExpect((ResultMatcher) jsonPath("$[1]").doesNotExist());
    }

    @Test
    void shouldReturnCreatedCategory_whenCreatingCategory() throws Exception{
        CategoryCreationRequest expectedRequest = new CategoryCreationRequest(
                "horror",
                "scary stories");
        Category categories = new Category("horror", "scary stories");
        when(categoriesServiceMock.addCategory(expectedRequest)).thenReturn(categories);
        String requestBody = """ 
                             { 
                               "category": %s, 
                               "name": "%s" 
                               "name":  "horror"
                               "description": "scary stories"
                             } 
                             """.formatted(  "horror",
                "scary stories");

        mockMvc.perform(post("/rest/products/add").contentType(MediaType.APPLICATION_JSON
                        )
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name").value(categories.getName()));
    }
}