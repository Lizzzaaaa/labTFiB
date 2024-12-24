package com.example.labTFiB.product;

import lombok.Builder;

@Builder
public record ProductDto(String title, Long year, String movieCategory, String movieDescription, Double price) {
}
