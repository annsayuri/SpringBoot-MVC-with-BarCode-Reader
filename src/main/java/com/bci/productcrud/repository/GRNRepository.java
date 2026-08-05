package com.bci.productcrud.repository;

import com.bci.productcrud.model.GRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GRNRepository extends JpaRepository<GRN, Long> {
    Optional<GRN> findByGrnNumber(String grnNumber);
}