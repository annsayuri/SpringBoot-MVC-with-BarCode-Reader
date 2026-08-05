package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import java.util.List;

public interface GRNService {
    List<GRN> getAllGRNs();
    GRN getGRNById(Long id);
    GRN createGRN(GRN grn);
}