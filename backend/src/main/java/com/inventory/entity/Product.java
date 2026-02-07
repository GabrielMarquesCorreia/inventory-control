package com.inventory.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal value;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<ProductRawMaterial> materials;

    // ===== GETTERS / SETTERS =====

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
