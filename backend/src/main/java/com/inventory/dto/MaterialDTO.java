package com.inventory.dto;

import com.inventory.entity.ProductRawMaterial;

public class MaterialDTO {

    public Long id;
    public String name;
    public Integer stock;
    public Integer quantity;

    public MaterialDTO(ProductRawMaterial prm) {

        this.id = prm.getRawMaterial().getId();
        this.name = prm.getRawMaterial().getName();
        this.stock = prm.getRawMaterial().getStock();
        this.quantity = prm.getQuantity();
    }
}
