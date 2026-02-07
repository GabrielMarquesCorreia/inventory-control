package com.inventory.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
public class Product {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    public List<ProductRawMaterial> materials;
}
