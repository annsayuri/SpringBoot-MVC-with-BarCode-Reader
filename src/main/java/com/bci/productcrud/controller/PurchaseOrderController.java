package com.bci.productcrud.controller;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.service.PurchaseOrderService;
import com.bci.productcrud.service.SupplierService;
import com.bci.productcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    private final PurchaseOrderService poService;
    private final SupplierService supplierService;
    private final ProductService productService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService poService, 
                                   SupplierService supplierService, 
                                   ProductService productService) {
        this.poService = poService;
        this.supplierService = supplierService;
        this.productService = productService;
    }

    // --- UI Views Routes ---

    @GetMapping
    public String listPurchaseOrders(Model model) {
        model.addAttribute("purchaseOrders", poService.getAllPurchaseOrders());
        return "po/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.findAll());
        return "po/form";
    }

    // --- REST API Endpoints ---

    @GetMapping("/api")
    @ResponseBody
    public List<PurchaseOrder> getAllPOApi() {
        return poService.getAllPurchaseOrders();
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<PurchaseOrder> createPOApi(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder createdPo = poService.createPurchaseOrder(purchaseOrder);
        return ResponseEntity.ok(createdPo);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<PurchaseOrder> getPOByIdApi(@PathVariable Long id) {
        return ResponseEntity.ok(poService.getPurchaseOrderById(id));
    }
}