package com.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductRawMaterial {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JsonIgnore
    public Product product;

    @ManyToOne
    public RawMaterial rawMaterial;

    public Integer quantity;
}
