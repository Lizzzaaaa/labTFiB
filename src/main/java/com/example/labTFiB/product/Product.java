package com.example.labTFiB.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "products")
@Getter
//@NoArgsConstructor
@ToString
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long productId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Long year;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", nullable = false)
    private String movieCategory;

    @Column(name = "movieDescription", nullable = false)
    private String movieDescription;

    @Column(name = "price", nullable = false)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    public Product(String title, Long year, String movieCategory, String movieDescription, Double price) {
        this.title = title;
        this.year = year;
        this.movieCategory = movieCategory;
        this.movieDescription = movieDescription;
        this.price = price;
    }
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }
}
