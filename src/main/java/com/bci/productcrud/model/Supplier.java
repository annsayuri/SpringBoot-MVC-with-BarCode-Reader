package com.bci.productcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "contact_person")
    private String contactPerson;

    @Email
    private String email;

    private String phone;

    private String address;

    // ========== NEW FIELDS (ER Diagram) ==========
    @Column(name = "bank_details")
    private String bankDetails;

    private String status;  // ACTIVE, INACTIVE

    @Column(nullable = false)
    private Boolean active = true;

    public Supplier(String name, String contactPerson, String email, String phone, String address) {
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = true;
        this.status = "ACTIVE";
    }
}