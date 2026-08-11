package com.bci.productcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String barcode;

    @NotBlank
    private String name;

    private String description;

    // ========== NEW FIELDS (ER Diagram) ==========
    private String category;
    private String brand;
    private String size;
    private String color;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    private Integer quantity;

    private String status;  // ACTIVE, INACTIVE, DISCONTINUED
}