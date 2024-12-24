package com.example.labTFiB.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductCreationRequest {
    @NotNull(message = "Title cannot be null")
    private final String title;

    @NotNull(message = "Year cannot be null")
    private final Long year;

    @NotNull(message = "Category cannot be null")
    private final String categoryName;

    @NotNull(message = "Description cannot be null")
    private final String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private final Double price;

    @JsonCreator
    ProductCreationRequest(@JsonProperty("title") String title,
                           @JsonProperty("year") Long year,
                           @JsonProperty("categoryName") String categoryName,
                           @JsonProperty("description") String description,
                           @JsonProperty("price") Double price){
        this.title = title;
        this.year = year;
        this.categoryName = categoryName;
        this.description = description;
        this.price = price;
    }
}
