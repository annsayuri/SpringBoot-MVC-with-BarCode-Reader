package com.bci.productcrud.controller;

import com.bci.productcrud.service.GRNService;
import com.bci.productcrud.service.ProductService;
import com.bci.productcrud.service.PurchaseOrderService;
import com.bci.productcrud.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private PurchaseOrderService poService;
    
    @Autowired
    private GRNService grnService;

    @GetMapping("/")
    public String home(Model model) {
        // Get counts for statistics
        try {
            model.addAttribute("productCount", productService.findAll().size());
        } catch (Exception e) {
            model.addAttribute("productCount", 0);
        }
        
        try {
            model.addAttribute("supplierCount", supplierService.getAllSuppliers().size());
        } catch (Exception e) {
            model.addAttribute("supplierCount", 0);
        }
        
        try {
            model.addAttribute("poCount", poService.getAllPurchaseOrders().size());
        } catch (Exception e) {
            model.addAttribute("poCount", 0);
        }
        
        try {
            model.addAttribute("grnCount", grnService.getAllGRNs().size());
        } catch (Exception e) {
            model.addAttribute("grnCount", 0);
        }
        
        return "index";
    }
}