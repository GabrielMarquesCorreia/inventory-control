package com.inventory.repository;

import com.inventory.entity.Product;
import com.inventory.entity.RawMaterial;
import com.inventory.entity.ProductRawMaterial;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRawMaterialRepository implements PanacheRepository<ProductRawMaterial> {

    public ProductRawMaterial findByProductAndRawMaterial(Product product, RawMaterial rawMaterial) {
        return find("product = ?1 and rawMaterial = ?2", product, rawMaterial).firstResult();
    }
}
