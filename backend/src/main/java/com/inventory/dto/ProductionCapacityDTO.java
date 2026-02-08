package com.inventory.dto;

public class ProductionCapacityDTO {

    public Long productId;
    public String productName;
    public Integer maxProduction;

    public ProductionCapacityDTO(
            Long productId,
            String productName,
            Integer maxProduction
    ) {
        this.productId = productId;
        this.productName = productName;
        this.maxProduction = maxProduction;
    }
}
