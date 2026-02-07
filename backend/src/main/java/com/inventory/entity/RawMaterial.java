package com.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RawMaterial {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "The material name is required")
    private String name;

    @NotNull(message = "The stock is required")
    @Min(value = 0, message = "The stock cannot be negative")
    @Column(name = "stock_quantity")
    private Integer stock;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
