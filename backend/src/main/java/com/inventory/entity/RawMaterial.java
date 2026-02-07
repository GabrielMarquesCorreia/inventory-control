package com.inventory.entity;

import jakarta.persistence.*;

@Entity
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer stockQuantity;

    // getters e setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    
}