package com.bci.productcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "grn_items")
public class GRNItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer receivedQuantity;
    private Double unitPrice;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "grn_id")
    @JsonIgnore
    private GRN grn;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public GRNItem() {
    }

    public GRNItem(Integer receivedQuantity, Double unitPrice, Double totalPrice, Product product) {
        this.receivedQuantity = receivedQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.product = product;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(Integer receivedQuantity) { this.receivedQuantity = receivedQuantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public GRN getGrn() { return grn; }
    public void setGrn(GRN grn) { this.grn = grn; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}