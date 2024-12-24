package com.example.labTFiB.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CategoryCreationRequest {
    private Long categoriesId;

    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;

    public CategoryCreationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
