package com.inventory.dto;

public class ProductionSuggestionDTO {

    public Long productId;
    public String productName;
    public int maxQuantity;
    public double totalValue;

    public ProductionSuggestionDTO(Long productId, String productName, int maxQuantity, double totalValue) {
        this.productId = productId;
        this.productName = productName;
        this.maxQuantity = maxQuantity;
        this.totalValue = totalValue;
    }
}
