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

import java.time.LocalDate;
import java.util.List;

@Service
public class GRNServiceImpl implements GRNService {

    private final GRNRepository grnRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository poRepository;

    @Autowired
    public GRNServiceImpl(GRNRepository grnRepository, 
                          ProductRepository productRepository, 
                          PurchaseOrderRepository poRepository) {
        this.grnRepository = grnRepository;
        this.productRepository = productRepository;
        this.poRepository = poRepository;
    }

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
                double itemTotal = item.getReceivedQuantity() * item.getUnitPrice();
                item.setTotalPrice(itemTotal);
                totalAmount += itemTotal;

                // 🚀 Stock Auto-Update Logic:
                // GRN එකක් ලැබුණු පසු Product එකේ Quantity එක වැඩි කිරීම
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                
                int currentQty = product.getQuantity() != null ? product.getQuantity() : 0;
                product.setQuantity(currentQty + item.getReceivedQuantity());
                productRepository.save(product);
            }
        }
        grn.setTotalAmount(totalAmount);

        // PO status එක "RECEIVED" ලෙස update කිරීම (PO එකක් තිබේ නම් පමණක්)
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