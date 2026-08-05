package com.bci.productcrud.service;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.model.PurchaseOrderItem;
import com.bci.productcrud.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository poRepository) {
        this.poRepository = poRepository;
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return poRepository.findAll();
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return poRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + id));
    }

    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setOrderDate(LocalDate.now());
        if (purchaseOrder.getStatus() == null) {
            purchaseOrder.setStatus("PENDING");
        }

        double totalAmount = 0.0;
        if (purchaseOrder.getItems() != null) {
            for (PurchaseOrderItem item : purchaseOrder.getItems()) {
                item.setPurchaseOrder(purchaseOrder);
                double itemTotal = item.getQuantity() * item.getUnitPrice();
                item.setTotalPrice(itemTotal);
                totalAmount += itemTotal;
            }
        }
        purchaseOrder.setTotalAmount(totalAmount);

        return poRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder updateStatus(Long id, String status) {
        PurchaseOrder po = getPurchaseOrderById(id);
        po.setStatus(status);
        return poRepository.save(po);
    }
}