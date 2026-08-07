package com.bci.productcrud.controller;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.service.ProductService;
import com.bci.productcrud.service.PurchaseOrderService;
import com.bci.productcrud.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService poService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    // Render PO List Page
    @GetMapping
    public String poList(Model model) {
        try {
            logger.info("Fetching all purchase orders...");
            model.addAttribute("purchaseOrders", poService.getAllPurchaseOrders());
            return "po/list";
        } catch (Exception e) {
            logger.error("Error fetching purchase orders: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to load purchase orders: " + e.getMessage());
            return "po/list";
        }
    }

    // Render Create PO Form
    @GetMapping("/new")
    public String poForm(Model model) {
        try {
            logger.info("Loading PO form...");
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            model.addAttribute("products", productService.findAll());
            return "po/form";
        } catch (Exception e) {
            logger.error("Error loading PO form: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to load form: " + e.getMessage());
            return "po/form";
        }
    }

    // JSON API for Form Submission
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<?> createPO(@RequestBody PurchaseOrder purchaseOrder) {
        try {
            PurchaseOrder savedPO = poService.createPurchaseOrder(purchaseOrder);
            return ResponseEntity.ok(savedPO);
        } catch (Exception e) {
            logger.error("Error creating PO: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}