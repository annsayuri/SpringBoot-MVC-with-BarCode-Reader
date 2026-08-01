package com.bci.productcrud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}