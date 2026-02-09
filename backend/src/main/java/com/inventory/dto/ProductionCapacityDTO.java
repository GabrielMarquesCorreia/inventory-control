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

    public Integer getMaxProduction() {
        return maxProduction;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
