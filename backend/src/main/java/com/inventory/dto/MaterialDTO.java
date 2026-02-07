package com.inventory.dto;

import com.inventory.entity.ProductRawMaterial;

public class MaterialDTO {

    public String materialName;
    public Integer quantity;

    public MaterialDTO(ProductRawMaterial prm){
        this.materialName = prm.rawMaterial.getName();
        this.quantity = prm.quantity;
    }
}
