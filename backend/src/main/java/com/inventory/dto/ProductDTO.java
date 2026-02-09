package com.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    public Long id;
    public String name;
    public BigDecimal value;

    public List<ProductRawMaterialDTO> materials;

    public static class ProductRawMaterialDTO {
        public Long id;
        public String name;
        public int quantity;
    }
}
