package com.bci.productcrud.controller;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.service.ProductService;
import com.bci.productcrud.service.PurchaseOrderService;
import com.bci.productcrud.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService poService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    // Render PO List Page
    @GetMapping
    public String poList(Model model) {
        model.addAttribute("purchaseOrders", poService.getAllPurchaseOrders());
        return "po/list";
    }

    // Render Create PO Form
    @GetMapping("/new")
    public String poForm(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.findAll());
        return "po/form";
    }

    // JSON API for Form Submission
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<?> createPO(@RequestBody PurchaseOrder purchaseOrder) {
        try {
           
        PurchaseOrder savedPO = poService.createPurchaseOrder(purchaseOrder);     
            return ResponseEntity.ok(savedPO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}