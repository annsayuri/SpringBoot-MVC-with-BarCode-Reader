package com.bci.productcrud.controller;

import com.bci.productcrud.model.Product;
import com.bci.productcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductWebController {

    @Autowired
    private ProductService productService;

    // List all products (HTML)
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    // Show create form (HTML)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/form";
    }

    // Save product (redirect)
    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.create(product);
        return "redirect:/products";
    }

    // Show edit form (HTML)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/form";
    }

    // Update product (redirect)
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.update(id, product);
        return "redirect:/products";
    }

    // Delete product (redirect)
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.delete(id);
        } catch (Exception e) {
            // Product might be referenced in PO items
            System.out.println("Cannot delete product: " + e.getMessage());
        }
        return "redirect:/products";
    }
}