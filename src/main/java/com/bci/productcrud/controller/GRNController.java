package com.bci.productcrud.controller;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.service.GRNService;
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
@RequestMapping("/grn")
public class GRNController {

    private final GRNService grnService;
    private final PurchaseOrderService poService;
    private final SupplierService supplierService;
    private final ProductService productService;

    @Autowired
    public GRNController(GRNService grnService, 
                         PurchaseOrderService poService, 
                         SupplierService supplierService, 
                         ProductService productService) {
        this.grnService = grnService;
        this.poService = poService;
        this.supplierService = supplierService;
        this.productService = productService;
    }

    // --- UI Views Routes ---

    @GetMapping
    public String listGRNs(Model model) {
        model.addAttribute("grns", grnService.getAllGRNs());
        return "grn/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("grn", new GRN());
        model.addAttribute("purchaseOrders", poService.getAllPurchaseOrders());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.findAll());
        return "grn/form";
    }

    // --- REST API Endpoints ---

    @GetMapping("/api")
    @ResponseBody
    public List<GRN> getAllGRNApi() {
        return grnService.getAllGRNs();
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<GRN> createGRNApi(@RequestBody GRN grn) {
        GRN createdGrn = grnService.createGRN(grn);
        return ResponseEntity.ok(createdGrn);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<GRN> getGRNByIdApi(@PathVariable Long id) {
        return ResponseEntity.ok(grnService.getGRNById(id));
    }
}