package com.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    public Long id;
    public String name;
    public BigDecimal value;

    // Optional: include materials if you want
    public List<ProductRawMaterialDTO> materials;

    // Nested DTO for materials
    public static class ProductRawMaterialDTO {
        public Long id;
        public String name;
        public int quantity;
    }
}
