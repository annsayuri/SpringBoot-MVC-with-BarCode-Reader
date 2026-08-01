package com.bci.productcrud.controller;

import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Supplier> create(@Valid @RequestBody Supplier supplier) {
        Supplier created = supplierService.saveSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Supplier> findAll() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> findById(@PathVariable Long id) {
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        return supplierService.getSupplierById(id)
                .map(existing -> {
                    supplier.setId(id);
                    return ResponseEntity.ok(supplierService.saveSupplier(supplier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (supplierService.getSupplierById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
