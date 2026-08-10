package com.bci.productcrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_receipts")
public class SalesReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private User cashier;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private BigDecimal discount;

    @Column(name = "payment_method")
    private String paymentMethod;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "salesReceipt", cascade = CascadeType.ALL)
    private List<SalesReceiptItem> items = new ArrayList<>();
}