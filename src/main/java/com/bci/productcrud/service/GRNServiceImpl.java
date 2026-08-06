package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.model.GRNItem;
import com.bci.productcrud.model.Product;
import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.repository.GRNRepository;
import com.bci.productcrud.repository.ProductRepository;
import com.bci.productcrud.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GRNServiceImpl implements GRNService {

    @Autowired
    private GRNRepository grnRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Override
    public List<GRN> getAllGRNs() {
        return grnRepository.findAll();
    }

    @Override
    public GRN getGRNById(Long id) {
        return grnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GRN not found with id: " + id));
    }

    @Override
    @Transactional
    public GRN createGRN(GRN grn) {
        grn.setReceivedDate(LocalDate.now());

        double totalAmount = 0.0;
        if (grn.getItems() != null) {
            for (GRNItem item : grn.getItems()) {
                item.setGrn(grn);
                
                // BigDecimal Price / Quantity Handling
                double price = item.getUnitPrice() != null ? item.getUnitPrice().doubleValue() : 0.0;
                int qty = item.getQuantity() != null ? item.getQuantity() : 0;
                
                double itemTotal = price * qty;
                item.setTotalPrice(BigDecimal.valueOf(itemTotal)); // 👈 Fix 1
                totalAmount += itemTotal;

                // 🚀 Stock Auto-Update Logic
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                int currentQty = product.getQuantity() != null ? product.getQuantity() : 0;
                product.setQuantity(currentQty + qty); // 👈 Fix 2
                productRepository.save(product);
            }
        }

        grn.setTotalAmount(totalAmount);

        // PO status update to RECEIVED
        if (grn.getPurchaseOrder() != null && grn.getPurchaseOrder().getId() != null) {
            PurchaseOrder po = poRepository.findById(grn.getPurchaseOrder().getId()).orElse(null);
            if (po != null) {
                po.setStatus("RECEIVED");
                poRepository.save(po);
            }
        }

        return grnRepository.save(grn);
    }
}