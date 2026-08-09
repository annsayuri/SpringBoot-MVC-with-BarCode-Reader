package com.bci.productcrud.controller;

import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierWebController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "supplier/list";  // ← මෙය "supplier/list" විය යුතුයි
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/form";  // ← මෙය "supplier/form" විය යුතුයි
    }

    @PostMapping
    public String createSupplier(@ModelAttribute Supplier supplier) {
        supplierService.saveSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "supplier/form";  // ← මෙය "supplier/form" විය යුතුයි
    }

    @PostMapping("/update/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute Supplier supplier) {
        supplier.setId(id);
        supplierService.saveSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}