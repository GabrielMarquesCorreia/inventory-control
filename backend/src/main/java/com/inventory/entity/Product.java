package com.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Product extends PanacheEntity {

    @Column(name = "price")
    private BigDecimal value;

    public String name;

    @OneToMany(mappedBy = "product")
    public List<ProductRawMaterial> materials;

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
