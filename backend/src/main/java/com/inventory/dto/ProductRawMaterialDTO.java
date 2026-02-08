package com.inventory.dto;

public class ProductRawMaterialDTO {
    public Long id;           
    public int quantity;       
    public RawMaterialDTO rawMaterial;

    public static class RawMaterialDTO {
        public Long id;
        public String name;
        public int stock;
    }
}
