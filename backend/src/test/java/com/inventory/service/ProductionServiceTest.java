package com.inventory.service;

import com.inventory.entity.Product;
import com.inventory.dto.ProductionPlanDTO;
import com.inventory.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class ProductServiceTest {

    @Test
    void shouldPrioritizeHigherValueProductsUnitTest() {
        ProductRepository productRepo = mock(ProductRepository.class);

        Product cheap = new Product();
        cheap.setName("Cheap");
        cheap.setValue(new BigDecimal("10"));

        Product expensive = new Product();
        expensive.setName("Expensive");
        expensive.setValue(new BigDecimal("100"));

        when(productRepo.listAll()).thenReturn(new ArrayList<>(List.of(cheap, expensive)));

        ProductService service = new ProductService();
        service.productRepository = productRepo;

        ProductionPlanDTO plan = service.calculateProductionPlan();
    }
}
