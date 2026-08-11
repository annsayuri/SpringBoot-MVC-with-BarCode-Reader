package com.bci.productcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "grn_items")
public class GRNItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grn_id")  // Matches ER Diagram
    @JsonIgnore
    private GRN grn;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity_received", nullable = false)
    private Integer quantity;

    @Column(name = "quantity_accepted")
    private Integer quantityAccepted;

    @Column(name = "quantity_rejected")
    private Integer quantityRejected;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public GRN getGrn() { return grn; }
    public void setGrn(GRN grn) { this.grn = grn; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; calculateTotalPrice(); }

    public Integer getQuantityAccepted() { return quantityAccepted; }
    public void setQuantityAccepted(Integer quantityAccepted) { this.quantityAccepted = quantityAccepted; }

    public Integer getQuantityRejected() { return quantityRejected; }
    public void setQuantityRejected(Integer quantityRejected) { this.quantityRejected = quantityRejected; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; calculateTotalPrice(); }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    private void calculateTotalPrice() {
        if (this.quantity != null && this.unitPrice != null) {
            this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}