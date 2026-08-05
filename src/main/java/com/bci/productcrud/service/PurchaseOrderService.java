package com.bci.productcrud.service;

import com.bci.productcrud.model.PurchaseOrder;
import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllPurchaseOrders();
    PurchaseOrder getPurchaseOrderById(Long id);
    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);
    PurchaseOrder updateStatus(Long id, String status);
}