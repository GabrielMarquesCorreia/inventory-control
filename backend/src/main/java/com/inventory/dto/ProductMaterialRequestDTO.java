package com.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductMaterialRequestDTO {

    @NotNull
    public Long rawMaterialId;

    @Min(1)
    public int quantity;
}
