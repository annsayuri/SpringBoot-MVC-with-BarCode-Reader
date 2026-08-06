package com.bci.productcrud.controller;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.service.GRNService;
import com.bci.productcrud.service.ProductService;
import com.bci.productcrud.service.PurchaseOrderService;
import com.bci.productcrud.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grn")
public class GRNController {

    @Autowired
    private GRNService grnService;

    @Autowired
    private PurchaseOrderService poService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    // Render GRN List Page
    @GetMapping
    public String grnList(Model model) {
        model.addAttribute("grns", grnService.getAllGRNs());
        return "grn/list";
    }

    // Render Create GRN Form
    @GetMapping("/new")
    public String grnForm(Model model) {
        model.addAttribute("purchaseOrders", poService.getAllPurchaseOrders());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.findAll());
        return "grn/form";
    }

    // JSON API for Form Submission & Stock Update
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<?> createGRN(@RequestBody GRN grn) {
        try {
            GRN savedGRN = grnService.createGRN(grn);
            return ResponseEntity.ok(savedGRN);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}