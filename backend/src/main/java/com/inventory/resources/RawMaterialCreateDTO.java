package com.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RawMaterialCreateDTO {

    @NotBlank
    public String name;

    @NotNull
    public Integer stockQuantity;
}
