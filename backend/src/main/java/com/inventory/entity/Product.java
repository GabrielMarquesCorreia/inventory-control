package com.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public BigDecimal value;

    @OneToMany(mappedBy = "product")
    public List<ProductRawMaterial> materials;

    // Add getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public List<ProductRawMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ProductRawMaterial> materials) {
        this.materials = materials;
    }
}
