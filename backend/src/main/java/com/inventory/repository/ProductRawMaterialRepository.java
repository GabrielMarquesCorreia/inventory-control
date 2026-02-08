package com.inventory.repository;

import com.inventory.entity.ProductRawMaterial;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRawMaterialRepository
        implements PanacheRepository<ProductRawMaterial> {
}
