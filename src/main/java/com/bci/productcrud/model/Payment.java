package com.bci.productcrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private SalesReceipt salesReceipt;  
    @Column(name = "payment_method")
    private String paymentMethod;  // CASH, CARD, BANK

    private BigDecimal amount;

    private String status;  // PAID, PENDING, FAILED
}