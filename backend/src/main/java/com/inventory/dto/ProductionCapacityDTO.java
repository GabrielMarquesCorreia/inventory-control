package com.inventory.dto;

public class ProductionCapacityDTO {

    public Long productId;
    public Integer maxProduction;

    public ProductionCapacityDTO(Long productId, Integer maxProduction) {
        this.productId = productId;
        this.maxProduction = maxProduction;
    }
}
