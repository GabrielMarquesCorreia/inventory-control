package com.inventory.service;

import com.inventory.dto.ProductionCapacityDTO;
import com.inventory.dto.ProductionPlanDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductRawMaterial;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.RawMaterialRepository;
import com.inventory.entity.RawMaterial;
import com.inventory.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import com.inventory.repository.ProductRawMaterialRepository;


import java.math.BigDecimal;
import java.util.ArrayList;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRawMaterialRepository productRawMaterialRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;


    public ProductionCapacityDTO calculateProductionCapacity(Long productId) {

        Product product = productRepository.findById(productId);

        if (product == null ||
            product.getMaterials() == null ||
            product.getMaterials().isEmpty()) {

            return new ProductionCapacityDTO(
                    productId,
                    product != null ? product.getName() : null,
                    0
            );
        }

        int maxProduction = Integer.MAX_VALUE;

        for (ProductRawMaterial prm : product.getMaterials()) {

            if (prm.getQuantity() <= 0) {
                continue;
            }

            int possibleProduction =
                    prm.getRawMaterial().getStock()
                            / prm.getQuantity();

            maxProduction = Math.min(maxProduction, possibleProduction);
        }

        if (maxProduction == Integer.MAX_VALUE) {
            maxProduction = 0;
        }

        return new ProductionCapacityDTO(
                product.getId(),
                product.getName(),
                maxProduction
        );
    }

    // ✅ CREATE
    @Transactional
    public Product create(Product product) {

        productRepository.persist(product);

        return product;
    }


    // ✅ FIND ALL
    public List<Product> findAll() {
        return productRepository.listAll();
    }


    // ✅ FIND BY ID
    public Product findById(Long id) {

        return productRepository
                .findByIdOptional(id)
                .orElseThrow(() ->
                    new NotFoundException("Product not found: " + id)
                );
    }

    // ✅ UPDATE
    @Transactional
    public Product update(Long id, Product updatedProduct) {

        Product existing = productRepository.findById(id);

        if (existing == null) {
            throw new NotFoundException("Product not found");
        }

        existing.setName(updatedProduct.getName());
        existing.setValue(updatedProduct.getValue());

        if (updatedProduct.getMaterials() != null) {
            existing.setMaterials(updatedProduct.getMaterials());
        }

        return existing;
    }


    // ✅ DELETE
    @Transactional
    public void delete(Long id) {

        Product product = productRepository.findById(id);

        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        productRepository.delete(product);
    }

    @Transactional
    public void addMaterial(Long productId, Long materialId, int quantity) {

        // Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Find product
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        // Find raw material
        RawMaterial material = rawMaterialRepository.findById(materialId);

        if (material == null) {
            throw new NotFoundException("RawMaterial not found");
        }

        // Initialize list if null
        if (product.getMaterials() == null) {
            product.setMaterials(new ArrayList<>());
        }

        // Check if material already exists
        boolean exists = product.getMaterials()
                .stream()
                .anyMatch(m -> m.getRawMaterial().getId().equals(materialId));

        if (exists) {
            throw new IllegalArgumentException("Material already added");
        }

        // Create relation entity
        ProductRawMaterial prm = new ProductRawMaterial();

        prm.setProduct(product);
        prm.setRawMaterial(material);
        prm.setQuantity(quantity);

        // ✅ Persist in database
        productRawMaterialRepository.persist(prm);

        // Add to product list
        product.getMaterials().add(prm);
    }

    public ProductionPlanDTO calculateProductionPlan() {

        List<Product> products = productRepository.listAll();

        products.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        ProductionPlanDTO plan = new ProductionPlanDTO();
        plan.items = new ArrayList<>();
        plan.totalValue = BigDecimal.ZERO;

        for (Product product : products) {

            ProductionCapacityDTO capacity =
                    calculateProductionCapacity(product.getId());

            int qty = capacity.maxProduction;

            if (qty <= 0) {
                continue;
            }

            ProductionPlanDTO.Item item =
                    new ProductionPlanDTO.Item();

            item.productName = product.getName();
            item.quantity = qty;

            item.value =
                    product.getValue()
                        .multiply(BigDecimal.valueOf(qty));

            plan.totalValue =
                    plan.totalValue.add(item.value);

            plan.items.add(item);
        }

        return plan;
    }

    public List<ProductionCapacityDTO> getAvailableProducts() {

        List<Product> products = productRepository.listAll();

        return products.stream()
                .map(p -> calculateProductionCapacity(p.getId()))
                .filter(cap -> cap.maxProduction > 0) // ✅ FIXED HERE
                .collect(Collectors.toList());
    }

}
