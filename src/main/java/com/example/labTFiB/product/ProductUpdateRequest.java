package com.example.labTFiB.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long productId;

    private String title;

    private Long year;
    private String movieCategory;
    private String movieDescription;
    private Long categoryId;
    private Double price;

    public ProductUpdateRequest(
            @JsonProperty("title") String title,
            @JsonProperty("year") Long year,
            @JsonProperty("movieCategory") String movieCategory,
            @JsonProperty("movieDescription") String movieDescription,
            @JsonProperty("price") Double price
    ){
        this.title = title;
        this.year = year;
        this.movieCategory = movieCategory;
        this.movieDescription = movieDescription;
        this.price = price;
    }
}
