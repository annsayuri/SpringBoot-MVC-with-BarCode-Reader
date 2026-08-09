package com.bci.productcrud.service;

import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        if (supplier.getId() == null) {
            supplier.setActive(true);  // New supplier is active by default
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        try {
            supplierRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Supplier is referenced in other tables
            // Soft delete - set active to false
            Supplier supplier = supplierRepository.findById(id).orElse(null);
            if (supplier != null) {
                supplier.setActive(false);
                supplierRepository.save(supplier);
            }
            throw new RuntimeException("Cannot delete supplier. It is referenced in other records. Supplier has been deactivated instead.");
        }
    }
}