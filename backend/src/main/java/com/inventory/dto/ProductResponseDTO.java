package com.inventory.dto;

import com.inventory.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDTO {

    public Long id;
    public String name;
    public List<MaterialDTO> materials;

    public ProductResponseDTO(Product product) {

        this.id = product.id;
        this.name = product.getName();

        if (product.getMaterials() != null) {
            this.materials = product.getMaterials()
                    .stream()
                    .map(MaterialDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
