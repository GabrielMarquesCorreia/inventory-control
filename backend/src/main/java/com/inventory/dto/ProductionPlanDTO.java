package com.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductionPlanDTO {

    public BigDecimal totalValue;
    public List<Item> items;

    public static class Item {

        public String productName;
        public Integer quantity;
        public BigDecimal value;
    }
}
